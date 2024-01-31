import Spinner from './Spinner';

function FullScreenSpinner() {
  return (
    <div className="absolute top-0 left-0 max-w-[420px] z-[9999] w-screen h-screen overflow-hidden flex justify-center items-center bg-[linear-gradient(rgba(0,0,0,0.5),rgba(0,0,0,0.5))]">
      <Spinner spinnerClassName="w-14 h-14" />
    </div>
  );
}

export default FullScreenSpinner;
