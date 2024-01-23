import { useState } from 'react';
import { toast } from 'react-toastify';
import { motion } from 'framer-motion';
import { useNavigate } from '@tanstack/react-router';

import { useLongPress } from '@/hooks';
import { cn, iconOpacity } from '@/utils';
import type { ScrollDirectionProps } from '@/@types';
import AccessibleIconButton from './AccessibleIconButton';

const FramerIconButton = motion(AccessibleIconButton);

function FloatingButton({ direction }: ScrollDirectionProps) {
  const navigate = useNavigate();
  const [isShowMenu, setIsShowMenu] = useState<boolean>(false);

  const attrs = useLongPress(
    () => {
      setIsShowMenu(true);
    },
    { threshold: 1500 },
  );

  return (
    <>
      {isShowMenu ? (
        <AccessibleIconButton
          iconType="x"
          width={16}
          height={16}
          stroke="blue-500"
          className="flex justify-center items-center bg-white rounded-full w-[44px] h-[44px] fixed right-[10px] bottom-[60px] border-[1px] border-grey-200 shadow-md z-[30]"
          label="게시글 작성 취소 버튼"
          onClick={() => setIsShowMenu(false)}
        />
      ) : (
        <FramerIconButton
          iconType="addText"
          width={24}
          height={24}
          className={cn(
            'flex justify-center items-center bg-black rounded-full w-[56px] h-[56px] fixed right-[10px] bottom-[60px] transition-colors',
            iconOpacity(direction),
          )}
          label="게시글 작성 플로팅 버튼"
          {...attrs}
          onClick={() => navigate({ to: '/post/edit' })}
          whileTap={{ scale: 0.8 }}
        />
      )}
      {isShowMenu && (
        <>
          <div
            role="none"
            className="bg-grey-200 opacity-60 w-full h-full absolute top-0 left-0 z-[10]"
            onClick={() => setIsShowMenu(false)}
          />
          <FramerIconButton
            initial={{
              right: 10,
              bottom: 60,
            }}
            animate={{
              right: 124,
              bottom: 78,
            }}
            iconType="image"
            width={24}
            height={24}
            fill="white"
            className="flex justify-center items-center bg-blue-500 rounded-full w-[56px] h-[56px] fixed shadow-md transition-transform z-[50]"
            label="이미지 첨부"
            onClick={() => navigate({ to: '/post/edit' })}
          />
          <FramerIconButton
            initial={{
              right: 10,
              bottom: 60,
            }}
            animate={{
              right: 88,
              bottom: 150,
            }}
            iconType="gif"
            width={24}
            height={24}
            fill="white"
            className="flex justify-center items-center bg-blue-500 rounded-full w-[56px] h-[56px] fixed shadow-md transition-transform z-[50]"
            label="GIF 첨부"
            onClick={() => toast('지원하지 않는 기능입니다.')}
          />
          <FramerIconButton
            initial={{
              right: 10,
              bottom: 60,
            }}
            animate={{
              right: 16,
              bottom: 185,
            }}
            iconType="addText"
            width={24}
            height={24}
            className="flex justify-center items-center bg-blue-500 rounded-full w-[56px] h-[56px] fixed shadow-md transition-transform z-[50]"
            label="게시글 작성"
            onClick={() => navigate({ to: '/post/edit' })}
          />
        </>
      )}
    </>
  );
}

export default FloatingButton;
