import { useState } from 'react';
import { toast } from 'react-toastify';
import type { ChangeEvent } from 'react';

import { StepTitle } from '..';
import { AccessibleIconButton, Button, Icon, Typography } from '../common';
import type { NavigationEvent } from './signUpReducer';

interface SignUpProfileImageBoxProps extends NavigationEvent {
  disabled: boolean;
  onChangeImage: (path: string) => void;
  onSignUp: VoidFunction;
}

function SignUpProfileImageBox({
  disabled,
  onNextStep,
  onChangeImage,
  onSignUp,
}: SignUpProfileImageBoxProps) {
  const [imageSrc, setImageSrc] = useState<string>('');

  const encodeFileToBase64 = (file: File) => {
    const reader = new FileReader();
    reader.readAsDataURL(file);

    reader.onloadend = async () => {
      setImageSrc(reader.result as string);
      // FIXME: 추후 upload api 연동 후 삭제
      onChangeImage(reader.result as string);
    };
  };

  const handleUploadImage = async (e: ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];

    if (file) {
      encodeFileToBase64(file);
    }
  };

  const handleClickNextButton = async () => {
    try {
      // TODO: image upload, optimize
      onNextStep();
      onChangeImage(imageSrc);
    } catch (err) {
      toast.error('업로드에 실패했습니다.');
    }
  };

  const handleClickSkipButton = async () => {
    try {
      onSignUp();
    } catch (err) {
      toast.error('잠시 문제가 생겼습니다.');
    }
  };

  return (
    <>
      <StepTitle
        title="프로필 사진 선택하기"
        description="마음에 드는 셀카 사진이 있나요? 지금 업로드하세요."
        className="mt-[28px]"
      />
      <div className="h-full flex flex-col gap-[40px] justify-center items-center">
        {/* Image Upload */}
        {imageSrc ? (
          <div className="relative w-[200px] h-[200px]">
            <img
              src={imageSrc}
              alt="preview"
              className="rounded-full w-full h-full"
            />
            <div className="rounded-full absolute flex justify-center right-2 bottom-2 bg-black w-[52px] h-[52px]">
              <AccessibleIconButton
                iconType="x"
                stroke="white"
                label="이미지 업로드 취소"
                width={32}
                height={32}
                onClick={() => setImageSrc('')}
              />
            </div>
          </div>
        ) : (
          <div className="flex items-center justify-center w-full">
            <label
              htmlFor="input-image"
              className="rounded-full bg-blueGrey-500 flex flex-col justify-center items-center gap-[16px] w-[200px] h-[200px] cursor-pointer border-1 border-black"
            >
              <Icon type="solidCamera" width={52} height={52} />
              <input
                id="input-image"
                type="file"
                accept="image/*"
                aria-label="image upload"
                className="hidden"
                onChange={handleUploadImage}
              />
            </label>
          </div>
        )}
      </div>
      <div className="flex flex-col gap-[26px]">
        <Button
          color="black"
          variant="filled"
          aria-label="기재해준 이메일을 통해 인증 후, 비밀번호 설정 페이지로 이동합니다."
          disabled={disabled}
          aria-disabled={disabled}
          onClick={handleClickNextButton}
        >
          <Typography size="body-2" color="white">
            다음
          </Typography>
        </Button>
        <Typography
          role="navigation"
          size="headline-8"
          color="grey-600"
          className="underline cursor-pointer text-center"
          onClick={handleClickSkipButton}
        >
          지금은 넘어가기
        </Typography>
      </div>
    </>
  );
}

export default SignUpProfileImageBox;
