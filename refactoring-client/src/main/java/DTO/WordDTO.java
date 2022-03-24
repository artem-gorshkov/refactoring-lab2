package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WordDTO {
    private String word;
    private List<String> prefixes;
    private String root;
    private List<String> suffixes;

    public void setWord(String word) {
        this.word = getPartValue(word);
    }

    public void setPrefixes(List<String> prefixes) {
        this.prefixes = getListValue(prefixes);
    }

    public void setRoot(String root) {
        this.root = getPartValue(root);
    }

    public void setSuffixes(List<String> suffixes) {
        this.suffixes = getListValue(suffixes);
    }

    private List<String> getListValue(List<String> list) {
        return list.stream().map(this::getPartValue).collect(Collectors.toList());
    }

    private String getPartValue(String part) {
        if (part != null && !part.equals("")) {
            return part.toLowerCase();
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        if (prefixes != null) {
            for (int i = 0; i < prefixes.size(); i++) {
                stringBuilder.append(prefixes.get(i));
                stringBuilder.append("-");
            }
        }

        if (root != null) {
            stringBuilder.append(root);
            stringBuilder.append("-");
        }

        if (suffixes != null && !suffixes.isEmpty()) {
            for (int i = 0; i < suffixes.size(); i++) {
                stringBuilder.append(suffixes.get(i));
                if (i != suffixes.size() - 1) {
                    stringBuilder.append("-");
                }
            }
        } else {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }

        return stringBuilder.toString();
    }
}
