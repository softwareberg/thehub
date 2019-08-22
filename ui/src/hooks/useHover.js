import { useEffect, useState } from 'react';

function useHover(callback) {
  const [isHover, setIsHover] = useState(false);
  useEffect(() => callback(isHover), [isHover, callback]);
  return {
    onMouseEnter: () => setIsHover(true),
    onMouseLeave: () => setIsHover(false)
  };
}

export default useHover;
