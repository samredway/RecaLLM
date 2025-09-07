package com.redway.recallm.constants;

public final class PromptConstants {
  private PromptConstants() {} // prevent instantiation

  /*
   * Used to summarise last session so as to pull it into short term memory
   * currently allows 500 words to summarise. This may need to be tweaked and
   * played with
   */
  public static final String SUMMARY_PROMPT =
      "Please take the following text and summarise it in such a way that you capture all the key"
          + " points and can continue a conversation without losing context but also reducing it to"
          + " a more concises form.\n"
          + " This summary should be no more than 500 words, so if the full text is less than 500"
          + " words then keep the whole text. Otherwise you will have to squash it down keeping the"
          + " main points. You should only return the summary itself and no other text. This"
          + " summary is used by a programatic process\n\n";

  public static final String SESSION_SUMMARY_PREFIX = "Here is a summary of our last session:\n\n";
}
