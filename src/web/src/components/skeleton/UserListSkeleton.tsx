import { cn } from '@/utils';

interface UserListSkeletonProps {
  className?: string;
}

function UserListSkeleton({ className }: UserListSkeletonProps) {
  return (
    <section
      className={cn('animate-pulse w-full flex flex-col gap-[32px]', className)}
    >
      <div className="w-full flex gap-[8px]">
        <div className="w-[40px] min-w-[40px] h-[40px] rounded-full bg-blueGrey-100 dark:bg-blueGrey-300" />

        <div className="w-full flex flex-col">
          <div className="w-full flex items-center justify-between">
            <div className="flex flex-col gap-[2px]">
              <div className="w-[90px] h-[24px] bg-blueGrey-100 dark:bg-blueGrey-300" />
              <div className="w-[60px] h-[24px] bg-blueGrey-100 dark:bg-blueGrey-300" />
            </div>
            <div className="w-[90px] h-[30px] bg-blueGrey-100 dark:bg-blueGrey-300 rounded-[12px]" />
          </div>
          <div className="w-full h-[80px] mt-[8px] bg-blueGrey-100 dark:bg-blueGrey-300" />
        </div>
      </div>

      <div className="w-full flex gap-[8px]">
        <div className="w-[40px] min-w-[40px] h-[40px] rounded-full bg-blueGrey-100 dark:bg-blueGrey-300" />

        <div className="w-full flex flex-col">
          <div className="w-full flex items-center justify-between">
            <div className="flex flex-col gap-[2px]">
              <div className="w-[80px] h-[24px] bg-blueGrey-100 dark:bg-blueGrey-300" />
              <div className="w-[120px] h-[24px] bg-blueGrey-100 dark:bg-blueGrey-300" />
            </div>
            <div className="w-[90px] h-[30px] bg-blueGrey-100 dark:bg-blueGrey-300 rounded-[12px]" />
          </div>
          <div className="w-full h-[40px] mt-[8px] bg-blueGrey-100 dark:bg-blueGrey-300" />
        </div>
      </div>

      <div className="w-full flex gap-[8px]">
        <div className="w-[40px] min-w-[40px] h-[40px] rounded-full bg-blueGrey-100 dark:bg-blueGrey-300" />

        <div className="w-full flex flex-col">
          <div className="w-full flex items-center justify-between">
            <div className="flex flex-col gap-[2px]">
              <div className="w-[160px] h-[24px] bg-blueGrey-100 dark:bg-blueGrey-300" />
              <div className="w-[60px] h-[24px] bg-blueGrey-100 dark:bg-blueGrey-300" />
            </div>
            <div className="w-[90px] h-[30px] bg-blueGrey-100 dark:bg-blueGrey-300 rounded-[12px]" />
          </div>
          <div className="w-full h-[40px] mt-[8px] bg-blueGrey-100 dark:bg-blueGrey-300" />
        </div>
      </div>

      <div className="w-full flex gap-[8px]">
        <div className="w-[40px] min-w-[40px] h-[40px] rounded-full bg-blueGrey-100 dark:bg-blueGrey-300" />

        <div className="w-full flex flex-col">
          <div className="w-full flex items-center justify-between">
            <div className="flex flex-col gap-[2px]">
              <div className="w-[80px] h-[24px] bg-blueGrey-100 dark:bg-blueGrey-300" />
              <div className="w-[60px] h-[24px] bg-blueGrey-100 dark:bg-blueGrey-300" />
            </div>
            <div className="w-[90px] h-[30px] bg-blueGrey-100 dark:bg-blueGrey-300 rounded-[12px]" />
          </div>
          <div className="w-full h-[40px] mt-[8px] bg-blueGrey-100 dark:bg-blueGrey-300" />
        </div>
      </div>
    </section>
  );
}

export default UserListSkeleton;
