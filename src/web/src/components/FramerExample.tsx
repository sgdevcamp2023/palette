import { motion } from 'framer-motion';

function FramerExample() {
  return (
    <motion.div
      initial={{ opacity: 0, scale: 0.5 }}
      animate={{ opacity: 1, scale: 1 }}
      transition={{ duration: 0.5 }}
      style={{
        width: 200,
        height: 200,
        backgroundColor: 'blue',
        borderRadius: '50%',
        content: '',
      }}
    />
  );
}

export default FramerExample;
