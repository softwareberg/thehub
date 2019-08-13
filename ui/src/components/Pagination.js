import React from 'react';
import { useDispatch, useSelector } from 'react-redux';
import PaginationBootstrap from 'react-bootstrap/Pagination';
import { setPage } from 'redux/actions/setPage';

const Pagination = () => {
  const { page, totalPages } = useSelector(state => state.pagination);
  const dispatch = useDispatch();

  return (
    <PaginationBootstrap size="lg">
      {Array(totalPages).fill(0).map((_, i) => (
        <PaginationBootstrap.Item
          key={i}
          active={i + 1 === page}
          onClick={() => dispatch(setPage(i + 1))}
        >
          {i + 1}
        </PaginationBootstrap.Item>
      ))
      }
    </PaginationBootstrap>
  );
};

export default Pagination;
