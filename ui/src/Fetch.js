export const Fetch = ({ cache, done, url, view }) => {
  if (cache[url] && cache[url] !== 'fetching') {
    return view();
  }
  if (cache[url] !== 'fetching') {
    fetch(url)
      .then(res => res.json())
      .then(json => done({ url, response: json }));
    done({ url, response: 'fetching' });
  }
};
