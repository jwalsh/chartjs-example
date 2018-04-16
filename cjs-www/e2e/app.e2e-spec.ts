import { CjsWwwPage } from './app.po';

describe('cjs-www App', function() {
  let page: CjsWwwPage;

  beforeEach(() => {
    page = new CjsWwwPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
