package com.b.r.loteriab.r.Services;

import com.b.r.loteriab.r.Model.Draw;
import com.b.r.loteriab.r.Model.NumberTwoDigits;
import com.b.r.loteriab.r.Repository.DrawRepository;
import com.b.r.loteriab.r.Validation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Dany on 04/05/2019.
 */
@Service
@Transactional
public class DrawService {
    @Autowired
    private DrawRepository drawRepository;

    public Result saveDraw (Draw draw){
        Result result = validateModel(draw);
        if (!result.isValid()){
            return result;
        }


        Draw savedDraw = drawRepository.findDrawByShiftNameAndDrawDate(draw.getShift().getName(), draw.getDrawDate());
        if (savedDraw!=null){
            result.add("Tiraj sa egziste deja");
            return result;
        }

        try {
            draw.setCreationDate(new Date());
            draw.setModificationDate(new Date());
            draw.setEnabled(true);
            drawRepository.save(draw);
        }catch (Exception ex){
            result.add("Tiraj la pa ka anrejistre reeseye ankò");
        }
        return  result;
    }

    private Result validateModel (Draw draw){
        Result result = new Result();

        if (draw.getNumberThreeDigits().getNumberInStringFormat().isEmpty()){
            result.add("Loto 3 chif la paka vid", "Shift");
            return result;
        }

        if (draw.getShift() == null){
            result.add("Tip tiraj la vid rantrel yon lot fwa", "NumberTwoDigits");
            return result;
        }

        if(draw.getNumberTwoDigits() == null || draw.getNumberTwoDigits().size() < 3)
        {
            result.add("Ou sipoze ajoute twa nimewo pou bòlet la");
            return  result;
        }

        if(!draw.getNumberTwoDigits().get(0).getNumberInStringFormat().equals(draw.getNumberThreeDigits().getNumberInStringFormat().substring(1)))
        {
            result.add("Premye lo a sipoze menm ak loto 3 chif la");
            return  result;
        }
        int index = 0;
        for (NumberTwoDigits numberTwoDigits: draw.getNumberTwoDigits()){
            if (numberTwoDigits.getNumberInStringFormat().isEmpty()){
                result.add((index+1) + " lo a manke, antrel epi retounen anrejistre");
            }
            index++;
        }


        return result;
    }


    public Result updateDraw(Draw draw) {
        Result result = validateModel(draw);
        if (!result.isValid()){
            return result;
        }
        List<Draw> savedDraw = drawRepository.findAllByShiftNameAndDrawDate(draw.getShift().getName(), draw.getDrawDate());
        if (savedDraw!=null){
            for (Draw sd : savedDraw){
                if (sd.getId().longValue() != draw.getId().longValue()){
                    result.add("Tiraj sa egziste deja");
                    return result;
                }
            }
        }

        Draw currentDraw = drawRepository.findDrawById(draw.getId());
        currentDraw.setModificationDate(new Date());
        currentDraw.setNumberThreeDigits(draw.getNumberThreeDigits());
        currentDraw.setNumberTwoDigits(draw.getNumberTwoDigits());
        currentDraw.setShift(draw.getShift());
        try {
            drawRepository.save(currentDraw);
        }catch (Exception ex){
            result.add("Tiraj la pa ka aktyalize reeseye ankò");
        }
        return  result;
    }

    public ArrayList<Draw> findAllDraw() {
        return (ArrayList<Draw>) drawRepository.findAll();
    }

    public Result deleteDrawById(Long id){
        Result result = new Result();
        Draw draw = drawRepository.findDrawById(id);
        if(draw == null) {
            result.add("Tiraj sa ou bezwen elimine a pa egziste");
            return result;
        }
        try{
            drawRepository.deleteById(id);
        }catch (Exception ex){
            result.add("Tiraj la pa ka elimine reeseye ankò");
        }
        return result;
    }

    public List<Draw> findAllDraw(int page, int itemPerPage, Boolean state, int day, int month, int year){

        String inputString = day + "/"+(month-1)+"/"+year;
        Date modificationDate = new Date();
        try {
             modificationDate = new SimpleDateFormat("dd/MM/yyyy").parse(inputString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Pageable pageable = PageRequest.of(page,itemPerPage);
        if (state != null){
           if(day > 0 && month<=0 && year <=0) {
              return drawRepository.selectDrawByDayAndEnabled(state, modificationDate, pageable);
           } else if (day > 0 && month > 0 && year <=0){
              return drawRepository.selectDrawByDayAndMonthAndEnabled(state, modificationDate, pageable);
           } else if (day > 0 && month <= 0 && year >0){
              return drawRepository.selectDrawByDayAndYearEnabled(state, modificationDate, pageable);
           } else if (day <= 0 && month > 0 && year <= 0){
              return drawRepository.selectDrawByMonthAndEnabled(state, modificationDate, pageable);
           } else if (day <= 0 && month > 0 && year > 0){
              return drawRepository.selectDrawByMonthAndYearEnabled(state, modificationDate, pageable);
           } else if (day <= 0 && month <= 0 && year >0){
              return drawRepository.selectDrawByYearAndEnabled(state, modificationDate, pageable);
           } else if (day > 0 && month > 0 && year >0){
              return drawRepository.findAllByEnabledAndModificationDate(state, modificationDate, pageable);
           } else if (day < 0 && month < 0 && year < 0){
               return drawRepository.findAllByEnabled (pageable, state);
           }

        } else {
            if(day > 0 && month<=0 && year <=0) {
                return drawRepository.selectDrawByDay(modificationDate, pageable);
            } else if (day > 0 && month > 0 && year <=0){
                return drawRepository.selectDrawByDayAndMonth(modificationDate, pageable);
            } else if (day > 0 && month <= 0 && year >0){
                return drawRepository.selectDrawByDayAndYear(modificationDate, pageable);
            } else if (day <= 0 && month > 0 && year <= 0){
                return drawRepository.selectDrawByMonth(modificationDate, pageable);
            } else if (day <= 0 && month > 0 && year > 0){
                return drawRepository.selectDrawByMonthAndYear(modificationDate, pageable);
            } else if (day <= 0 && month <= 0 && year >0){
                return drawRepository.selectDrawByYear(modificationDate, pageable);
            } else if (day > 0 && month > 0 && year >0){
                return drawRepository.findAllByModificationDate( modificationDate, pageable);
            }
        }
        return drawRepository.findAll ();
    }

    public Draw findDrawById(Long id){

        return drawRepository.findDrawById(id);
    }

     public List<Draw> findAllDrawByEnabled(Boolean enabled){
        if (enabled!= null){
            return drawRepository.findAllByEnabled(enabled);
        }
         return drawRepository.findAll();
     }

}
