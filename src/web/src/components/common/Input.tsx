import { cva } from 'class-variance-authority';
import type { InputHTMLAttributes } from 'react';

import Icon from './Icon';
import { cn } from '@/utils';
import type { IconKeyType } from './Icon';
import type { ColorType } from '@/@types';

const InputVariants = cva<{
  status: Record<'normal' | 'success' | 'error' | 'dirty', string>;
}>(
  'peer h-full w-full border-b border-grey-400 bg-transparent text-body-2 outline outline-0 transition-all pt-[8px] placeholder-shown:border-grey-400 placeholder:opacity-0 focus:outline-0 focus:placeholder:opacity-100',
  {
    variants: {
      status: {
        normal: 'text-blueGrey-800 focus:border-blue-500',
        success: 'text-blue-500 focus:border-blue-500',
        error: 'text-pink-200 focus:border-pink-200',
        dirty: 'text-blueGrey-800 focus:border-blue-500',
      },
    },
  },
);

const LabelVariants = cva<{
  status: Record<'normal' | 'success' | 'error' | 'dirty', string>;
}>(
  `pointer-events-none absolute left-0 -top-[8px] flex h-full w-full select-none !overflow-visible truncate text-body-2 text-blueGrey-800 transition-all after:content[''] after:absolute after:block after:w-full after:scale-x-0 after:border-b-2 after:border-blue-500 after:transition-transform after:duration-300 peer-placeholder-shown:text-body-2 peer-placeholder-shown:leading-[4.25] peer-focus:leading-tight peer-focus:text-body-2 peer-disabled:text-transparent peer-disabled:peer-placeholder-shown:text-blueGrey-800`,
  {
    variants: {
      status: {
        normal: 'peer-focus:text-blue-500',
        error: 'peer-focus:text-pink-200',
        success: 'peer-focus:text-blue-500',
        dirty: 'peer-focus:text-blue-500',
      },
    },
  },
);

interface InputProps extends InputHTMLAttributes<HTMLInputElement> {
  /**
   * label에 사용할 아이디입니다.
   */
  id?: string;
  label?: string;
  status?: 'normal' | 'success' | 'error' | 'dirty';
  icon?: {
    type: IconKeyType;
    fill?: ColorType;
    stroke?: ColorType;
    onClick?: VoidFunction;
  };
}

function Input({
  id,
  label,
  icon,
  className,
  status = 'normal',
  ...props
}: InputProps) {
  const isShowIcon =
    status === 'success' || (icon?.type && icon.type !== 'check');

  return (
    <div className={cn('relative h-11 w-full min-w-[200px]', className)}>
      {isShowIcon && (
        <div className="absolute grid w-5 h-5 place-items-center top-2/4 right-3 -translate-y-2/4 pt-[8px]">
          <Icon
            type={icon?.type ?? 'check'}
            aria-hidden
            fill={icon?.fill}
            stroke={icon?.stroke}
            onClick={icon?.onClick}
            className={icon?.onClick ? 'cursor-pointer' : ''}
          />
        </div>
      )}

      <input
        id={id}
        type="text"
        placeholder={label}
        aria-invalid={status === 'error'}
        className={InputVariants({ status })}
        {...props}
      />
      {label && (
        <label htmlFor={id} className={LabelVariants({ status })}>
          {label}
        </label>
      )}
    </div>
  );
}

export default Input;
