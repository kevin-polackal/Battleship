package cs3500.pa04;

import java.io.IOException;

/***Mock class to test failures in writing
 */
public class MockAppendable implements Appendable {
  /***Throws an IOException
   *
   * @param csq
   *         The character sequence to append.  If {@code csq} is
   *         {@code null}, then the four characters {@code "null"} are
   *         appended to this Appendable.
   *
   * @return null
   *
   * @throws IOException purposeful throw
   */
  @Override
  public Appendable append(CharSequence csq) throws IOException {
    throwInOut();
    return null;
  }


  /***Throws an IOException
   *
   * @param csq
   *         The character sequence from which a subsequence will be
   *         appended.  If {@code csq} is {@code null}, then characters
   *         will be appended as if {@code csq} contained the four
   *         characters {@code "null"}.
   *
   * @param start
   *         The index of the first character in the subsequence
   *
   * @param end
   *         The index of the character following the last character in the
   *         subsequence
   *
   * @return null
   *
   * @throws IOException purposeful throw
   */
  @Override
  public Appendable append(CharSequence csq, int start, int end) throws IOException {
    throwInOut();
    return null;
  }

  /***Throws an IOException
   *
   * @param c
   *         The character to append
   *
   * @return null
   *
   * @throws IOException purposeful throw
   */
  @Override
  public Appendable append(char c) throws IOException {
    throwInOut();
    return null;
  }

  /***Throws an IOException
   *
   * @throws IOException purposeful throw
   */
  private void throwInOut() throws IOException {

    throw new IOException("java.io.IOException: Mock throwing an error");
  }
}
