import { type HTMLAttributes, type ElementType, memo } from 'react';

interface VisuallyHiddenProps extends HTMLAttributes<HTMLSpanElement> {
  as?: ElementType;
}

/**
 * 사용자가 시각적으로는 보이지 않지만, 스크린리더에서만 읽을 수 있는 컴포넌트입니다.
 * 접근성을 높이기 위해, 스크린리더에서만 읽는 텍스트를 높이기 위해 사용합니다.
 *
 * @example
 * ```tsx
 * function HomeIcon() {
 *   return (
 *      <button type="button">
 *        <VisuallyHidden>
 *          <div role="text">홈 화면으로 가는 아이콘</div>
 *        </VisuallyHidden>
 *        <Icon type="home" onClick={() => toHome()} />
 *      </button>
 *   )
 * }
 * ```
 */
function VisuallyHidden({ as, ...props }: VisuallyHiddenProps) {
  const Component = as || 'span';

  return (
    <Component
      style={{
        position: 'absolute',
        width: '1px',
        height: '1px',
        padding: 0,
        margin: '-1px',
        borderWidth: 0,
        clip: 'rect(0 0 0 0)',
        overflow: 'hidden',
        whiteSpace: 'nowrap',
        ...props.style,
      }}
      {...props}
    />
  );
}

const MemoizedVisuallyHidden = memo(VisuallyHidden);

export default MemoizedVisuallyHidden;
