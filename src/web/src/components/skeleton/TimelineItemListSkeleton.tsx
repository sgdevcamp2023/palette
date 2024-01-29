import { cn } from '@/utils';

interface TimelineItemListSkeletonProps {
  className?: string;
}

function TimelineItemListSkeleton({
  className,
}: TimelineItemListSkeletonProps) {
  return (
    <div
      className={cn(
        'animate-pulse mt-3 flex flex-col gap-[12px] divide-y divide-y-blueGrey-400',
        className,
      )}
    >
      <div className="w-full flex gap-[8px]">
        <div className="rounded-full w-[44px] min-w-[44px] h-[44px] bg-blueGrey-100 dark:bg-blueGrey-300" />
        <div className="w-full flex flex-col gap-[4px]">
          <div className="flex justify-between items-center">
            <div className="w-[80px] h-[24px] rounded-[8px] bg-blueGrey-100 dark:bg-blueGrey-300" />
            <div className="w-[20px] h-[20px] rounded-full bg-blueGrey-100 dark:bg-blueGrey-300" />
          </div>
          <div className="w-full h-[60px] rounded-[8px] bg-blueGrey-100 dark:bg-blueGrey-300" />
        </div>
      </div>

      <div className="w-full flex gap-[8px] pt-[12px]">
        <div className="rounded-full w-[44px] min-w-[44px] h-[44px] bg-blueGrey-100 dark:bg-blueGrey-300" />
        <div className="w-full flex flex-col gap-[4px]">
          <div className="flex justify-between items-center">
            <div className="w-[120px] h-[24px] rounded-[8px] bg-blueGrey-100 dark:bg-blueGrey-300" />
            <div className="w-[20px] h-[20px] rounded-full bg-blueGrey-100 dark:bg-blueGrey-300" />
          </div>
          <div className="w-full h-[40px] rounded-[8px] bg-blueGrey-100 dark:bg-blueGrey-300" />
          <div className="w-full h-[160px] rounded-[8px] bg-blueGrey-100 dark:bg-blueGrey-300" />
        </div>
      </div>

      <div className="w-full flex gap-[8px] pt-[12px]">
        <div className="rounded-full w-[44px] min-w-[44px] h-[44px] bg-blueGrey-100 dark:bg-blueGrey-300" />
        <div className="w-full flex flex-col gap-[4px]">
          <div className="flex justify-between items-center">
            <div className="w-[50px] h-[24px] rounded-[8px] bg-blueGrey-100 dark:bg-blueGrey-300" />
            <div className="w-[20px] h-[20px] rounded-full bg-blueGrey-100 dark:bg-blueGrey-300" />
          </div>
          <div className="w-full h-[160px] rounded-[8px] bg-blueGrey-100 dark:bg-blueGrey-300" />
        </div>
      </div>
    </div>
  );
}

export default TimelineItemListSkeleton;
