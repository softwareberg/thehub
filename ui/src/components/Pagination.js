import React from 'react';
import { useDispatch, useSelector } from 'react-redux';
import PaginationBootstrap from 'react-bootstrap/Pagination';
import { setPage } from 'redux/actions/setPage';

const Pagination = () => {
  const { page, totalPages } = useSelector(state => state.pagination);
  const dispatch = useDispatch();

  const indices = [];
  for (let i = 1; i <= totalPages; i += 1) {
    indices.push(i);
  }

  return (
    <PaginationBootstrap style={{ flexWrap: 'wrap' }}>
      {indices.map(i => (
        <PaginationBootstrap.Item
          key={i}
          active={i === page}
          onClick={() => dispatch(setPage(i))}
        >
          {i}
        </PaginationBootstrap.Item>
      ))
      }
    </PaginationBootstrap>
  );
};

export default Pagination;
