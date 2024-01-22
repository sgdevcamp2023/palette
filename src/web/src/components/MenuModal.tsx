import { useState } from 'react';
import { AnimatePresence, motion } from 'framer-motion';
import { toast } from 'react-toastify';
import { useNavigate } from '@tanstack/react-router';

import type { User } from '@/@types';
import { Icon, Typography } from './common';
import AccessibleIconButton from './AccessibleIconButton';

interface MenuModalProps {
  user: User;
  onClose: VoidFunction;
}

function MenuModal({ user, onClose }: MenuModalProps) {
  const navigate = useNavigate();
  const [isShowToggle, setIsShowToggle] = useState<boolean>(false);

  const handleClickNotSupport = () => toast('아직 지원되지 않는 기능입니다.');

  return (
    <motion.div
      key="menu-modal"
      className="absolute z-[50] w-full px-[28px] h-full bg-white"
      initial={{ x: -375 }}
      animate={{ x: 0 }}
      transition={{
        type: 'keyframes',
      }}
    >
      <div className="flex justify-between items-center mt-[24px]">
        <img
          src={user.profileImagePath}
          alt="user profile"
          className="w-[40px] min-w[40px] h-[40px] min-h-[40px] rounded-full"
        />
        <AccessibleIconButton
          iconType="x"
          width={20}
          height={20}
          label="메뉴 닫기"
          onClick={onClose}
        />
      </div>
      <Typography size="headline-7" color="grey-600" className="mt-[10px]">
        {user.nickname}
      </Typography>
      <Typography size="body-1" color="blueGrey-800" className="mt-[8px]">
        {user.username}
      </Typography>
      <div className="flex gap-[10px] mt-[12px]">
        {/* TODO: Following, Follower Page로 이동 */}
        <div
          role="button"
          tabIndex={0}
          className="flex gap-[4px]"
          onClick={() =>
            navigate({ to: '/profile/$userId', params: { userId: user.id } })
          }
        >
          <Typography as="span" size="headline-8" color="grey-600">
            {user.followings}
          </Typography>
          <Typography as="span" size="body-2" color="blueGrey-800">
            팔로잉
          </Typography>
        </div>
        <div className="flex gap-[4px]">
          <Typography as="span" size="headline-8" color="grey-600">
            {user.followers}
          </Typography>
          <Typography as="span" size="body-2" color="blueGrey-800">
            팔로워
          </Typography>
        </div>
      </div>

      {/* 메뉴 */}
      <ul className="flex flex-col gap-[30px] mt-[32px]">
        <li>
          <button
            type="button"
            className="flex gap-[24px] items-center"
            onClick={() =>
              navigate({ to: '/profile/$userId', params: { userId: user.id } })
            }
          >
            <Icon type="user" width={24} height={24} />
            <Typography
              size="headline-5"
              color="grey-600"
              className="ml-[-4px]"
            >
              프로필
            </Typography>
          </button>
        </li>
        <li>
          <button
            type="button"
            className="flex gap-[24px] items-center"
            onClick={handleClickNotSupport}
          >
            <Icon type="bookmark" width={20} height={20} />
            <Typography size="headline-5" color="grey-600">
              북마크
            </Typography>
          </button>
        </li>
        <li>
          <button
            type="button"
            className="flex gap-[24px] items-center"
            onClick={handleClickNotSupport}
          >
            <Icon type="list" width={20} height={20} />
            <Typography size="headline-5" color="grey-600">
              리스트
            </Typography>
          </button>
        </li>
        <li>
          <button
            type="button"
            className="flex gap-[24px] items-center"
            onClick={handleClickNotSupport}
          >
            <Icon type="mic" width={20} height={20} />
            <Typography size="headline-5" color="grey-600">
              스페이스
            </Typography>
          </button>
        </li>
        <li>
          <button
            type="button"
            className="flex gap-[24px] items-center"
            onClick={handleClickNotSupport}
          >
            <Icon type="money" width={20} height={20} />
            <Typography size="headline-5" color="grey-600">
              수익 창출
            </Typography>
          </button>
        </li>
      </ul>

      <span className="flex w-full h-[1px] content-[''] my-[32px] bg-blueGrey-200" />

      <div
        className="flex justify-between items-center"
        role="button"
        tabIndex={0}
        onClick={() => setIsShowToggle((prev) => !prev)}
      >
        <Typography size="headline-8" color="grey-600">
          설정 및 지원
        </Typography>
        <Icon
          type="downArrow"
          width={16}
          height={16}
          className="transition-transform"
          style={{
            transform: isShowToggle ? 'rotate(180deg)' : 'rotate(0)',
          }}
        />
      </div>

      <AnimatePresence>
        {isShowToggle && (
          <motion.div
            initial={{ opacity: 0 }}
            animate={{ opacity: 100 }}
            exit={{ opacity: 0 }}
            className="flex flex-col gap-[24px] mt-[24px]"
          >
            {/* TODO: 설정 페이지로 이동 */}
            <div
              className="flex gap-[20px] items-center"
              role="button"
              tabIndex={0}
              onClick={handleClickNotSupport}
            >
              <Icon type="setting" width={20} height={20} />
              <Typography size="body-2" color="grey-600">
                설정 및 개인정보 보호
              </Typography>
            </div>
            <div
              className="flex gap-[20px] items-center ml-[-2px]"
              role="button"
              tabIndex={0}
              onClick={handleClickNotSupport}
            >
              <Icon type="circleQuestion" width={22} height={22} />
              <Typography size="body-2" color="grey-600" className="ml-[-2px]">
                고객 센터
              </Typography>
            </div>
            <div
              className="flex gap-[20px] items-center ml-[-2px]"
              role="button"
              tabIndex={0}
              onClick={handleClickNotSupport}
            >
              <Icon type="cart" width={20} height={20} />
              <Typography size="body-2" color="grey-600">
                구매
              </Typography>
            </div>
          </motion.div>
        )}
      </AnimatePresence>
    </motion.div>
  );
}

export default MenuModal;
