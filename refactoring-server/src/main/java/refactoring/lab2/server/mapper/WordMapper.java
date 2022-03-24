package refactoring.lab2.server.mapper;

import org.springframework.stereotype.Service;
import refactoring.lab2.server.dto.WordDTO;
import refactoring.lab2.server.entity.Prefix;
import refactoring.lab2.server.entity.Root;
import refactoring.lab2.server.entity.Suffix;
import refactoring.lab2.server.entity.Word;
import refactoring.lab2.server.exception.ComposedWordIsNotValid;
import refactoring.lab2.server.service.PrefixService;
import refactoring.lab2.server.service.RootService;
import refactoring.lab2.server.service.SuffixService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WordMapper {

    private final PrefixService prefixService;
    private final SuffixService suffixService;
    private final RootService rootService;

    public WordMapper(PrefixService prefixService, SuffixService suffixService, RootService rootService) {
        this.prefixService = prefixService;
        this.suffixService = suffixService;
        this.rootService = rootService;
    }

    public WordDTO entityToDto(Word word) {
        WordDTO wordDTO = new WordDTO();

        List<String> prefixes = word.getPrefixes().stream()
                .map(Prefix::getPrefix).collect(Collectors.toList());
        wordDTO.setPrefixes(prefixes);

        if (word.getRoot() != null) {
            wordDTO.setRoot(word.getRoot().getRoot());
        } else {
            wordDTO.setRoot(null);
        }

        List<String> suffixes = word.getSuffixes().stream()
                .map(Suffix::getSuffix).collect(Collectors.toList());
        wordDTO.setSuffixes(suffixes);

        wordDTO.setWord(word.getWord());

        return wordDTO;
    }

    public List<WordDTO> entitiesToDtos(List<Word> words) {
        return words.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    public Word dtoToEntity(WordDTO wordDTO) {
        if (!wordDTO.checkIfWorldIsValid()) {
            throw new ComposedWordIsNotValid(wordDTO.getWord() + "'s parts are not valid");
        }

        Word word = new Word();

        int partsNumber = 0;

        if (wordDTO.getPrefixes() != null && !wordDTO.getPrefixes().isEmpty()) {
            List<Prefix> prefixes = wordDTO.getPrefixes().stream()
                    .map(prefixService::getByPrefixAndSaveIfNotExists).collect(Collectors.toList());

            word.setPrefixes(prefixes);
            partsNumber = partsNumber + prefixes.size();
        }

        if (wordDTO.getPrefixes() != null && !wordDTO.getSuffixes().isEmpty()) {
            List<Suffix> suffixes = wordDTO.getSuffixes().stream()
                    .map(suffixService::getBySuffixAndSaveIfNotExists).collect(Collectors.toList());

            word.setSuffixes(suffixes);
            partsNumber = partsNumber + suffixes.size();
        }

        if (wordDTO.getRoot() != null) {
            Root root = rootService.getByRootAndSaveIfNotExists(wordDTO.getRoot());
            word.setRoot(root);
            partsNumber++;
        }

        if (wordDTO.getSuffixes() != null && !wordDTO.getSuffixes().isEmpty()) {
            List<Suffix> suffixes = wordDTO.getSuffixes().stream()
                    .map(suffixService::getBySuffixAndSaveIfNotExists).collect(Collectors.toList());

            word.setSuffixes(suffixes);
            partsNumber = partsNumber + suffixes.size();
        }

        word.setPartsNumber(partsNumber);
        word.setWord(wordDTO.getWord());

        return word;

    }

}
