
package com.alkemy.HFC.disney.validations;

import org.springframework.stereotype.Component;
import com.alkemy.HFC.disney.dto.*;
import java.util.List;

@Component
public class DTOValidations {
    
     public boolean movieDTOIsValid(MovieDTO movieDTO) {
        
         boolean valid = false;
       
         if (movieDTO.getImage() != null &&
                movieDTO.getTitle() != null &&
                movieDTO.getCreationDate() != null &&
                movieDTO.getRating() >= 1 &&
                movieDTO.getRating() <= 5 &&
                movieDTO.getCharacters() != null &&
                movieDTO.getGenres() != null) {
            if (!movieDTO.getCharacters().isEmpty()) {
                valid = iterateCharacters(movieDTO.getCharacters());
            }
            if (!movieDTO.getCharacters().isEmpty()) {
                valid = iterateGenres(movieDTO.getGenres());
            }
        }
        return valid;
    }

    private boolean iterateCharacters(List<CharacterDTO> charactersDTO) {
        for (CharacterDTO characterDto : charactersDTO) {
            if (!characterDTOIsValid(characterDto)) {
                return false;
            }
        }
        return true;
    }

    public boolean characterDTOIsValid(CharacterDTO characterDto) {
        return characterDto.getImage() != null &&
                characterDto.getName() != null &&
                characterDto.getBiography()!= null &&
                characterDto.getAge() > 0 &&
                characterDto.getWeight() > 0;
    }

    private boolean iterateGenres(List<GenreDTO> genres) {
        for (GenreDTO genreDTO : genres) {
            if (!genreDTOIsValid(genreDTO)) {
                return false;
            }
        }
        return true;
    }

    public boolean genreDTOIsValid(GenreDTO genreDTO) {
        return genreDTO.getImage() != null &&
                genreDTO.getName() != null;
    }

    public boolean movieDTOToUpdateIsValid(MovieDTO movieDTO) {
        return movieDTO.getImage() != null &&
                movieDTO.getTitle() != null &&
                movieDTO.getCreationDate() != null &&
                movieDTO.getRating() >= 1 &&
                movieDTO.getRating() <= 5;
    }
}
