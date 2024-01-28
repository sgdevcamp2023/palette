import { useState } from 'react';
import { toast } from 'react-toastify';
import type { ChangeEvent } from 'react';
import { useRouter } from '@tanstack/react-router';

import type { EditPaint } from '@/@types';
import { useAutoHeightTextArea } from '@/hooks';
import { EditPostCancelBottomSheet } from '@/components/bottomSheet';
import {
  DUMMY_USER,
  convertToMedia,
  countByte,
  forEditPaint,
  generateLocalStorage,
} from '@/utils';
import {
  AccessibleIconButton,
  Button,
  CircularProgress,
  Header,
  Icon,
  TempSavedPostModal,
  Typography,
} from '@/components';
import { editPostRoute } from '@/routes';

const EMPTY_LENGTH = 0;
const MAX_BYTE = 280;

const showOnlyOneToast = () => {
  if (typeof window === 'undefined') return;

  const $toast = window.document.querySelector('.Toastify__toast-container');
  if (Number($toast?.children.length ?? 0) === 1) return;

  toast(`최대 ${MAX_BYTE}만큼 적을 수 있습니다.`);
};

const tempSavedStorage =
  generateLocalStorage<EditPaint[]>('temp-saved-storage');

function PostEditPage() {
  const user = DUMMY_USER;
  const router = useRouter();
  const search = editPostRoute.useSearch();
  const [editPostInfo, setEditPostInfo] = useState<EditPaint>(forEditPaint({}));
  const textAreaRef = useAutoHeightTextArea(editPostInfo.text);
  const [image, setImage] = useState<string>('');
  const isNotDirty =
    editPostInfo.text.length === EMPTY_LENGTH && image.length === EMPTY_LENGTH;

  const [isModalOpen, setIsModalOpen] = useState<{
    cancel: boolean;
    tempSaved: boolean;
  }>({ cancel: false, tempSaved: false });
  const hasTempSavedPost = !!tempSavedStorage.get()?.length;
  const [tempSavedPost] = useState<EditPaint[]>(
    () => tempSavedStorage.get() ?? [],
  );

  const encodeFileToBase64 = (file: File) => {
    const reader = new FileReader();
    reader.readAsDataURL(file);

    reader.onloadend = async () => {
      setImage(reader.result as string);
    };
  };

  const handleUploadImage = async (e: ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];

    if (file) {
      encodeFileToBase64(file);
    }
  };

  const handleClickBackButton = () => {
    if (isNotDirty) {
      router.history.back();
      return;
    }
    setIsModalOpen((prev) => ({ ...prev, cancel: true }));
  };

  const handleChangeTextInput = (e: ChangeEvent<HTMLTextAreaElement>) => {
    // limit maxByte
    if (countByte(e.target.value) > MAX_BYTE) {
      showOnlyOneToast();
      return;
    }
    setEditPostInfo((prev) => ({ ...prev, text: e.target.value }));
  };

  const handleClickNotSupport = () => toast('아직 지원되지 않는 기능입니다.');

  const handleSubmitPost = async () => {
    toast(
      JSON.stringify(
        forEditPaint({
          text: editPostInfo.text,
          medias: image ? [convertToMedia(image, 'image')] : [],
          quotePaintId: search.postId,
        }),
      ),
    );
  };

  return (
    <>
      <Header
        left={{
          type: 'leftStickArrow',
          label: '뒤로가기',
          width: 24,
          height: 24,
          onClick: handleClickBackButton,
        }}
        right={{
          type: 'node',
          node: (
            <div className="relative">
              <Button
                color="blue"
                disabled={isNotDirty}
                className="w-[64px] py-1 px-0"
                onClick={handleSubmitPost}
              >
                <Typography
                  role="button"
                  size="body-3"
                  color={isNotDirty ? 'blue-200' : 'white'}
                  className="font-bold"
                >
                  게시하기
                </Typography>
              </Button>
              {hasTempSavedPost && (
                <Typography
                  role="button"
                  size="body-3"
                  color="blue-500"
                  className="absolute right-[100px] top-[4px] whitespace-nowrap font-bold cursor-pointer transition-colors hover:text-blue-300"
                  onClick={() =>
                    setIsModalOpen((prev) => ({
                      ...prev,
                      tempSaved: true,
                    }))
                  }
                >
                  임시 보관함
                </Typography>
              )}
            </div>
          ),
        }}
      />
      <main className="pt-[64px] pb-[40px] max-h-screen overflow-y-scroll">
        <div className="px-[10px] flex gap-[8px]">
          <img
            src={user.profileImagePath}
            alt="user"
            className="w-[34px] h-[34px] rounded-full"
          />
          <div className="w-full flex flex-col gap-[16px]">
            <textarea
              ref={textAreaRef}
              value={editPostInfo.text}
              onChange={handleChangeTextInput}
              autoCapitalize="sentences"
              autoCorrect="on"
              autoComplete="on"
              spellCheck="true"
              dir="auto"
              aria-label="글 작성"
              maxLength={MAX_BYTE}
              placeholder="무슨 일이 일어나고 있나요?"
              className="w-full min-h-[128px] max-h-[400px] resize-none text-[18px] text-grey-600
                          placeholder:text-blueGrey-800 placeholder:text-[18px]
                          focus:outline-0
                        "
            />
            {/* 이미지 */}
            {image && (
              <div className="relative">
                <img
                  src={image}
                  alt="user-uploaded"
                  className="w-full rounded-[12px] mb-4 overflow-clip"
                />
                <AccessibleIconButton
                  iconType="circleX"
                  label="이미지 업로드 취소"
                  className="[&_circle]:fill-black opacity-70 absolute right-[8px] top-[8px]"
                  width={20}
                  height={20}
                  onClick={() => setImage('')}
                />
              </div>
            )}
          </div>
        </div>

        {/* 메뉴 */}
        <div className="flex items-center h-[46px] border-y">
          <button
            type="button"
            className="px-[14px] flex gap-[8px] items-center"
          >
            <Icon type="globe" width={16} height={16} />
            <Typography size="body-3" color="blue-500">
              모든 사람이 답글을 달 수 있습니다.
            </Typography>
          </button>
        </div>
        <div className="px-[14px] h-[46px] flex justify-between items-center">
          <nav role="navigation" aria-live="polite">
            <div className="flex gap-[26px]">
              <AccessibleIconButton
                iconType="record"
                width={20}
                height={20}
                label="녹음 하기 버튼"
                onClick={handleClickNotSupport}
              />
              <label htmlFor="input-image">
                <Icon
                  type="image"
                  width={20}
                  height={20}
                  fill="blue-500"
                  className="cursor-pointer"
                />
                <input
                  id="input-image"
                  type="file"
                  accept="image/*"
                  aria-label="image upload"
                  className="hidden"
                  onChange={handleUploadImage}
                />
              </label>
              <AccessibleIconButton
                iconType="gif"
                width={20}
                height={20}
                fill="blue-500"
                label="GIF 추가 버튼"
                onClick={handleClickNotSupport}
              />
              <AccessibleIconButton
                iconType="vote"
                width={20}
                height={20}
                label="투표 하기 버튼"
                onClick={handleClickNotSupport}
              />
              <AccessibleIconButton
                iconType="location"
                width={20}
                height={20}
                label="위치 추가하기 버튼"
                onClick={handleClickNotSupport}
              />
            </div>
          </nav>

          <div className="flex divide-x divide-blueGrey-200">
            <CircularProgress
              value={countByte(editPostInfo.text)}
              minValue={0}
              maxValue={MAX_BYTE}
              className="mr-4"
            />
            <AccessibleIconButton
              iconType="circlePlus"
              width={20}
              height={20}
              stroke="white"
              className={`${
                isNotDirty ? 'opacity-40' : ''
              } pl-4 transition-opacity`}
              label="게시물 추가 버튼"
              onClick={handleClickNotSupport}
            />
          </div>
        </div>
      </main>

      {/* 모달 */}
      {isModalOpen.cancel && (
        <EditPostCancelBottomSheet
          isOpen={isModalOpen.cancel}
          onClose={() => setIsModalOpen((prev) => ({ ...prev, cancel: false }))}
          onClickDelete={() => {
            router.history.back();
            setIsModalOpen((prev) => ({ ...prev, cancel: false }));
          }}
          onClickSave={() => {
            router.history.back();
            tempSavedStorage.set([
              ...tempSavedPost,
              {
                ...forEditPaint({ text: editPostInfo.text }),
                medias: image ? [convertToMedia(image, 'image')] : [],
              },
            ]);
            setIsModalOpen((prev) => ({ ...prev, cancel: false }));
          }}
        />
      )}

      {isModalOpen.tempSaved && (
        <TempSavedPostModal
          onClose={() =>
            setIsModalOpen((prev) => ({ ...prev, tempSaved: false }))
          }
          setImage={setImage}
          setEditPostInfo={setEditPostInfo}
        />
      )}
    </>
  );
}

export default PostEditPage;
