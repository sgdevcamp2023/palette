import { createContext, useContext } from 'react';

interface ModalEvent {
  isOpen: boolean;
  onClose: VoidFunction;
}

const INITIAL_MODAL_VALUE = {
  isOpen: false,
  onClose: () => {},
};

export const ModalContext = createContext<ModalEvent>(INITIAL_MODAL_VALUE);

export const useModalContext = () => useContext<ModalEvent>(ModalContext);
