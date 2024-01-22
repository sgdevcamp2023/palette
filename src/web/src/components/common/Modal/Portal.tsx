import ReactDOM from 'react-dom';
import type { ReactNode } from 'react';
import { useEffect, useState } from 'react';

interface PortalProps {
  id: string;
  children: ReactNode;
}

function Portal({ id, children }: PortalProps) {
  const [element, setElement] = useState<HTMLElement | null>(null);

  useEffect(() => {
    const portalElement = window.document.getElementById(id);

    if (portalElement) {
      setElement(portalElement);
      return () => {
        portalElement?.remove();
      };
    }

    const newPortalElementInstance = document.createElement('div');
    newPortalElementInstance.id = id;
    newPortalElementInstance.style.cssText = `
      position: fixed;
      top: 0;
      left: 0;
      bottom: 0;
      right: 0;
      z-index: 9995;
    `;
    window.document.body.appendChild(newPortalElementInstance);
    setElement(newPortalElementInstance);

    return () => {
      newPortalElementInstance?.remove();
    };
  }, [id]);

  if (!element) {
    return null;
  }
  return ReactDOM.createPortal(children, element);
}

export default Portal;
