import type { ChangeEvent } from 'react';
import { memo, useState } from 'react';
import { toast } from 'react-toastify';
import { useMutation } from '@tanstack/react-query';

import { apis } from '@/api';
import { FullScreenSpinner } from '../skeleton';
import { Button, Icon, Typography } from '../common';
import { AccessibleIconButton, StepTitle } from '..';
import type { NavigationEvent } from './joinReducer';
import { forCloudinaryImage } from '@/utils';

interface JoinProfileImageBoxProps extends NavigationEvent {
  disabled: boolean;
  imageSrc?: string;
  onChangeImage: (path: string) => void;
  onJoin: VoidFunction;
}

function JoinProfileImageBox({
  disabled,
  imageSrc,
  onNextStep,
  onChangeImage,
  onJoin,
}: JoinProfileImageBoxProps) {
  const [imageCDNInfo, setImageCDNInfo] = useState<{
    publicId: string;
    signature: string;
    timestamp: number;
  }>({ publicId: '', signature: '', timestamp: Math.round(Date.now() / 1000) });

  const uploadMutation = useMutation({
    mutationKey: ['image-upload', imageSrc],
    mutationFn: (image: File) =>
      apis.images.uploadImage(image, imageCDNInfo.timestamp, {
        folder: 'profile',
      }),
    onSuccess: (res) => {
      onChangeImage(res.public_id);
      setImageCDNInfo((prev) => ({
        ...prev,
        publicId: res.public_id,
        signature: res.signature,
      }));
    },
    onError: () => {
      toast.error('업로드에 실패했습니다.');
      setImageCDNInfo((prev) => ({
        ...prev,
        timeStamp: Math.round(Date.now() / 1000),
      }));
    },
  });

  const handleClickDeleteImage = () => {
    onChangeImage('');
    setImageCDNInfo({
      timestamp: Math.round(Date.now() / 1000),
      publicId: '',
      signature: '',
    });
  };

  const handleUploadImage = (e: ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];

    if (!file) return;
    uploadMutation.mutate(file);
  };

  const handleClickSkipButton = async () => {
    try {
      onJoin();
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
              src={forCloudinaryImage(imageSrc, {
                resize: true,
                width: 200,
                height: 200,
              })}
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
                onClick={handleClickDeleteImage}
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
          disabled={disabled}
          aria-disabled={disabled}
          onClick={onNextStep}
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
      {uploadMutation.isPending && (
        <FullScreenSpinner className="left-1/2 -translate-x-1/2" />
      )}
    </>
  );
}

const MemoizedJoinProfileImageBox = memo(JoinProfileImageBox);

export default MemoizedJoinProfileImageBox;
