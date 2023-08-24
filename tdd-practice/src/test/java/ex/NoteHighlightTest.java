package ex;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class NoteHighlightTest {

    @Test
    void highlightTest() {
        assertThat(highlight("abc")).isEqualTo("abc");
        assertThat(highlight("efg")).isEqualTo("efg");
        assertThat(highlight("note")).isEqualTo("{note}");
        assertThat(highlight("1 note")).isEqualTo("1 {note}");
        assertThat(highlight("1 note 2")).isEqualTo("1 {note} 2");
        assertThat(highlight("keynote")).isEqualTo("keynote");
        assertThat(highlight("ke1note")).isEqualTo("ke1note");
        assertThat(highlight("ke2note")).isEqualTo("ke2note");
        assertThat(highlight("keanote")).isEqualTo("keanote");
        assertThat(highlight("yes note1")).isEqualTo("yes note1");
        assertThat(highlight("yes notea")).isEqualTo("yes notea");
        assertThat(highlight("not a note")).isEqualTo("not a {note}");
        assertThat(highlight("not a note note")).isEqualTo("not a {note} {note}");
        assertThat(highlight("not a note anote")).isEqualTo("not a {note} anote");
        assertThat(highlight("not a note anote note aa")).isEqualTo("not a {note} anote {note} aa");
        assertThat(highlight("not a note anote note aa note 11")).isEqualTo("not a {note} anote {note} aa {note} 11");
    }

    private String highlight(String str) {
        String result = "";
        int noteLength = "note".length();

        while (true) {
            int idx = str.indexOf("note");
            if (idx == -1) {
                result += str;
                break;
            }

            if (isPreChNotSpace(str, idx) || isPostChNotSpace(str, idx)) {
                result += str.substring(0, idx + noteLength);
            } else {
                String preStr = idx > 0 ? str.substring(0, idx) : "";
                result += preStr + "{note}";
            }

            str = str.substring(idx + noteLength);
        }
        return result;
    }


    private boolean isPreChNotSpace(String str, int idx) {
        int preChIdx = idx - 1;
        return preChIdx >= 0 && isNotSpace(str.charAt(preChIdx));
    }

    private boolean isPostChNotSpace(String str, int idx) {
        int postChIdx = idx + "note".length();
        return postChIdx < str.length() && isNotSpace(str.charAt(postChIdx));
    }

    private boolean isNotSpace(char pre) {
        return (pre >= 'a' && pre <= 'y') || (pre >= '0' && pre <= '9');
    }
}
