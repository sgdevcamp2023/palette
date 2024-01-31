import { memo } from 'react';
import { AnimatePresence, motion } from 'framer-motion';

import { cn } from '@/utils';

interface CircularProgressProps {
  value: number;
  minValue?: number;
  maxValue?: number;
  size?: number;
  className?: string;
}

function CircularProgress({
  value,
  className,
  minValue = 0,
  maxValue = 100,
  size = 16,
}: CircularProgressProps) {
  const radius = 45;
  const circumference = Math.ceil(2 * Math.PI * radius);
  const fillPercents = Math.abs(
    Math.ceil(
      (circumference / (maxValue - minValue)) * (value - (maxValue - minValue)),
    ),
  );
  const LIMIT_SHOW_TEXT = 10;

  return (
    <div className={cn('flex justify-center items-center', className)}>
      <div
        className="relative"
        style={{
          height: size,
        }}
      >
        <svg
          viewBox="0 0 100 100"
          version="1.1"
          xmlns="http://www.w3.org/2000/svg"
          width={size}
          height={size}
        >
          <circle
            cx="50"
            cy="50"
            r={radius}
            className="stroke-grey-200 stroke-[10]"
            fill="transparent"
          />
        </svg>
        <svg
          viewBox="0 0 100 100"
          width={size}
          height={size}
          className="absolute top-0 -rotate-90 overflow-visible"
        >
          <motion.circle
            cx="50"
            cy="50"
            r={radius}
            className={`stroke-[10] ${
              value === maxValue ? 'stroke-red-100' : 'stroke-blue-500'
            }`}
            fill="transparent"
            strokeDashoffset={fillPercents}
            strokeDasharray={circumference}
          />
        </svg>
        <AnimatePresence>
          {maxValue - value < LIMIT_SHOW_TEXT && (
            <motion.div
              key={maxValue - value}
              initial={{
                opacity: 0,
                top: 'calc(50% - 5px)',
              }}
              animate={{
                opacity: 1,
                top: '50%',
              }}
              exit={{
                opacity: 0,
                top: 'calc(50% + 5px)',
              }}
              className={`absolute -translate-y-1/2 left-1/2 -translate-x-1/2 text-[8px] transition-transforms ${
                maxValue - value === 0 ? 'text-red-100' : 'text-blue-500'
              }`}
            >
              {maxValue - value}
            </motion.div>
          )}
        </AnimatePresence>
      </div>
    </div>
  );
}

const MemoizedCircularProgress = memo(CircularProgress);

export default MemoizedCircularProgress;
