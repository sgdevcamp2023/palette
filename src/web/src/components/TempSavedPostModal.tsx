import { memo, useState } from 'react';
import { motion } from 'framer-motion';
import type { Dispatch, SetStateAction } from 'react';

import Header from './Header';
import type { EditPaint } from '@/@types';
import { Icon, Typography } from './common';
import { generateLocalStorage } from '@/utils';

interface TempSavedPostModalProps {
  onClose: VoidFunction;
  setImage: Dispatch<SetStateAction<string>>;
  setEditPostInfo: Dispatch<SetStateAction<EditPaint>>;
}

const tempSavedStorage =
  generateLocalStorage<EditPaint[]>('temp-saved-storage');

function TempSavedPostModal({
  onClose,
  setImage,
  setEditPostInfo,
}: TempSavedPostModalProps) {
  const [isEditMode, setIsEditMode] = useState<boolean>(false);
  const [isSelectedIndexes, setIsSelectedIndexes] = useState<number[]>([]);
  const [tempSavedPost, setTempSavedPost] = useState<EditPaint[]>(
    () => tempSavedStorage.get() ?? [],
  );

  const handleClickCancelOrConfirm = () => {
    if (isEditMode) {
      const nextTempSavedPost = [...tempSavedPost].filter(
        (_, index) =>
          isSelectedIndexes.findIndex((idx) => idx === index) === -1,
      );
      setTempSavedPost(nextTempSavedPost);
      tempSavedStorage.set(nextTempSavedPost);
      setIsSelectedIndexes([]);
    }
    setIsEditMode((prev) => !prev);
  };

  const handleClickDeleteToggleItem = (index: number) => {
    if (isSelectedIndexes.findIndex((idx) => idx === index) !== -1) {
      setIsSelectedIndexes((prev) => [...prev].filter((idx) => idx !== index));
    } else {
      setIsSelectedIndexes((prev) => [...prev, index]);
    }
  };

  const handleClickSelectItem = (item: EditPaint) => {
    const nextTempSavedPost = [...tempSavedPost].filter(
      (post) => JSON.stringify(post) !== JSON.stringify(item),
    );
    if (item.medias.length) setImage(item.medias[0].path);
    setEditPostInfo(item);
    setTempSavedPost(nextTempSavedPost);
    tempSavedStorage.remove();
    tempSavedStorage.set(nextTempSavedPost);
    onClose();
  };

  return (
    <motion.div
      key="temp-saved-modal"
      className="absolute top-0 z-[50] w-full h-full bg-white max-w-[420px]"
      initial={{ opacity: 0 }}
      animate={{ opacity: 1 }}
      transition={{
        type: 'keyframes',
      }}
    >
      <Header
        left={{
          type: 'text',
          label: '취소',
          onClick: onClose,
        }}
        center={{
          type: 'text',
          label: '임시 보관함',
          className: 'text-700',
        }}
        right={{
          type: 'text',
          label: isEditMode ? '완료' : '편집',
          onClick: handleClickCancelOrConfirm,
        }}
      />
      <div className="mt-[44px] mb-[50px]">
        <ul className="w-full divide-y divide-blueGrey-200">
          {tempSavedPost.map((post, index) => (
            <li key={post.text} className="w-full transition-colors">
              <button
                type="button"
                className={`w-full flex gap-[18px] p-[12px] justify-between items-center transition-colors ${
                  isEditMode ? 'hover:bg-grey-200' : ''
                }`}
                onClick={() =>
                  isEditMode
                    ? handleClickDeleteToggleItem(index)
                    : handleClickSelectItem(post)
                }
              >
                <div className="flex gap-[10px]">
                  {isEditMode && (
                    <Icon
                      type={
                        isSelectedIndexes.findIndex((idx) => idx === index) !==
                        -1
                          ? 'check'
                          : 'emptyCircle'
                      }
                      width={22}
                      height={22}
                    />
                  )}
                  <Typography size="body-2">{post.text}</Typography>
                </div>
                {post.medias.length > 0 && (
                  <img
                    src={post.medias[0].path}
                    alt="uploaded"
                    className="w-[62px] h-[62px] rounded-[12px]"
                  />
                )}
              </button>
            </li>
          ))}
        </ul>
      </div>
    </motion.div>
  );
}

const MemoizedTempSavedPostModal = memo(TempSavedPostModal);

export default MemoizedTempSavedPostModal;
