import type { ButtonHTMLAttributes } from 'react';

import type { IconKeyType } from './Icon';
import type { ColorType } from '@/@types';
import Icon from './Icon';
import VisuallyHidden from './VisuallyHidden';

interface AccessibleIconButtonProps
  extends ButtonHTMLAttributes<HTMLButtonElement> {
  iconType: IconKeyType;
  /**
   * 스크린 리더기에서만 읽을 수 있는 라벨입니다.
   */
  label: string;
  width?: number | string;
  height?: number | string;
  fill?: ColorType;
  stroke?: ColorType;
}

function AccessibleIconButton({
  iconType,
  label,
  width,
  height,
  fill,
  stroke,
  ...props
}: AccessibleIconButtonProps) {
  return (
    <button type="button" {...props}>
      <VisuallyHidden>
        <div role="text">{label}</div>
      </VisuallyHidden>
      <Icon
        type={iconType}
        fill={fill}
        stroke={stroke}
        width={width}
        height={height}
      />
    </button>
  );
}

export default AccessibleIconButton;
