import type { ButtonHTMLAttributes } from 'react';
import { cva } from 'class-variance-authority';
import { cn } from '@/utils';

const ButtonVariants = cva<{
  variant: Record<'filled' | 'outlined', string>;
  color: Record<'blue' | 'black', string>;
  padding: Record<'sm' | 'md' | 'lg', string>;
}>(
  'flex justify-center items-center w-full py-[15px] text-headline-5 rounded-3xl transition-colors',
  {
    variants: {
      variant: {
        filled: 'text-white',
        outlined: 'bg-white border border-black',
      },
      color: {
        blue: 'bg-blue-500',
        black: 'bg-black',
      },
      padding: {
        sm: 'py-[10px]',
        md: 'py-[15px]',
        lg: 'py-[20px]',
      },
    },
    defaultVariants: {
      variant: 'filled',
      color: 'black',
      padding: 'md',
    },
  },
);

interface ButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {
  variant?: 'filled' | 'outlined';
  color?: 'black' | 'blue';
  padding?: 'sm' | 'md' | 'lg';
}

function Button({
  className,
  children,
  disabled,
  padding,
  variant,
  color = 'black',
  ...props
}: ButtonProps) {
  return (
    <button
      type="button"
      disabled={disabled}
      aria-disabled={disabled}
      className={cn(
        ButtonVariants({
          variant,
          color,
          padding,
        }),
        className,
        disabled
          ? 'bg-grey-400'
          : `cursor-pointer ${
              color === 'black' ? 'hover:bg-grey-500' : 'hover:bg-blue-400'
            }`,
      )}
      {...props}
    >
      {children}
    </button>
  );
}

export default Button;
