import { useNavigate, useRouter } from '@tanstack/react-router';
import AccessibleIconButton from './AccessibleIconButton';
import { cn } from '@/utils';

interface ProfileHeaderProps {
  className?: string;
  isExpandImage: boolean;
}

function ProfileHeader({ isExpandImage, className }: ProfileHeaderProps) {
  const router = useRouter();
  const navigate = useNavigate();

  return (
    <header
      className={cn(
        'absolute top-0 flex justify-between items-center px-[14px] w-[420px] max-w-full h-[44px] z-[100]',
        className,
      )}
    >
      <AccessibleIconButton
        iconType="leftStickArrow"
        label="뒤로가기"
        fill="white"
        width={isExpandImage ? 20 : 16}
        height={isExpandImage ? 20 : 16}
        className="absolute left-[20px] z-50"
        onClick={() => router.history.back()}
      />
      <div
        className="relative bg-black opacity-60 rounded-full w-[32px] h-[32px] flex justify-center items-center"
        style={{
          width: isExpandImage ? 32 : 28,
          height: isExpandImage ? 32 : 28,
        }}
      />
      <AccessibleIconButton
        iconType="search"
        label="검색 페이지"
        fill="white"
        width={isExpandImage ? 16 : 12}
        height={isExpandImage ? 16 : 12}
        className="absolute right-[22px] z-50"
        onClick={() => navigate({ to: '/search' })}
      />
      <div
        className="relative bg-black opacity-60 rounded-full w-[32px] h-[32px] flex justify-center items-center"
        style={{
          width: isExpandImage ? 32 : 28,
          height: isExpandImage ? 32 : 28,
        }}
      />
    </header>
  );
}

export default ProfileHeader;
