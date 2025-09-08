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
          + " points or what we discussed. This summary will be used as part of the prompt for our"
          + " very next conversation. You should present all key points clearly - perhaps using"
          + " headings and bullet points, and add as much detail as required so as to not lose"
          + " important context. This summary should be no more than 500 words You should only"
          + " return the summary itself and no other text. This summary is used by a programatic"
          + " process\n\n";

  public static final String SESSION_SUMMARY_PREFIX =
      "Here is a summary of our last session. This is your memory of the last conversation you had"
          + " with me:\n\n";
}
