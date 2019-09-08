package com.b.r.loteriab.r.Services;

import com.b.r.loteriab.r.Model.*;
import com.b.r.loteriab.r.Model.Enums.CombinationTypes;
import com.b.r.loteriab.r.Model.Enums.Shifts;
import com.b.r.loteriab.r.Model.Interaces.CombinationViewModel;
import com.b.r.loteriab.r.Model.ViewModel.DrawViewModel;
import com.b.r.loteriab.r.Repository.DrawRepository;
import com.b.r.loteriab.r.Repository.SaleRepository;
import com.b.r.loteriab.r.Repository.ShiftRepository;
import com.b.r.loteriab.r.Validation.Helper;
import com.b.r.loteriab.r.Validation.NumberHelper;
import com.b.r.loteriab.r.Validation.Result;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
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

    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private CombinationTypeService combinationTypeService;

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private ShiftRepository shiftRepository;

    public Result saveDraw (Draw draw, Enterprise enterprise){
        Result result = validateModel(draw);
        if (!result.isValid()){
            return result;
        }

        Draw savedDraw = drawRepository.findDrawByShiftNameAndDrawDateAndEnterpriseId(draw.getShift().getName(), draw.getDrawDate(), enterprise.getId());
        if (savedDraw!=null){
            result.add("Tiraj sa egziste deja");
            return result;
        }

        try {
            draw.setEnterprise(enterpriseService.findEnterpriseByName(enterprise.getName()));
            draw.setCreationDate(new Date());
            draw.setModificationDate(new Date());
            draw.setEnabled(true);
            draw.setAmountLost(0.0);
            draw.setAmountWon(0.0);
            draw.setAmountSold(0.0);
            drawRepository.save(draw);

//            Draw currentDraw = drawRepository.findTopByEnterpriseIdOrderByEnterpriseIdDesc(enterprise.getId());
            Draw currentDraw = drawRepository.findByDrawDateAndEnterpriseIdAndShiftId(Helper.setTimeToDate(draw.getDrawDate(), "00:00:00".split(":")),enterprise.getId(), draw.getShift().getId());
            currentDraw.setAmountSold(determineAmountForSoldTicket(draw).getAmountSold());
            determineWonTicket(draw, false);
            currentDraw.setAmountLost(determineAmountLostForSoldTicket(draw).getAmountLost()); //  what the enterpriseLost
            currentDraw.setAmountWon(currentDraw.getAmountSold() - currentDraw.getAmountLost()); // what the enterprise won
            drawRepository.save(currentDraw);
        }catch (Exception ex){
            result.add("Tiraj la pa ka anrejistre reeseye ankò");
        }
        return  result;
    }

    private Result validateModel (Draw draw){
        Result result = new Result();

        if (Long.parseLong(NumberHelper.getNumericValue(draw.getId()).toString()) <= 0){
            if (draw.getShift() == null){
                result.add("Ou dwe rantre tiraj la", "NumberTwoDigits");
                return result;
            }
            if (draw.getDrawDate() == null){
                result.add("Ou dwe rantre yon dat pou tiraj la", "NumberTwoDigits");
                return result;
            }
            if (!draw.getDrawDate().before(new Date())){
                result.add("Dat tiraj la ou mete a poko rive rantre yn lot", "NumberTwoDigits");
                return result;
            }
        }
        if (draw.getNumberThreeDigits().getNumberInStringFormat().isEmpty()){
            result.add("Loto 3 chif la paka vid", "Shift");
            return result;
        }


        if(draw.getFirstDraw() == null)
        {
            result.add("Ou sipoze ajoute premye lo pou bòlet la");
            return  result;
        }

        if(draw.getSecondDraw() == null)
        {
            result.add("Ou sipoze ajoute dezyem lo pou bòlet la");
            return  result;
        }

        if(draw.getThirdDraw() == null)
        {
            result.add("Ou sipoze ajoute twazyem lo pou bòlet la");
            return  result;
        }

        if(!draw.getFirstDraw().getNumberInStringFormat().equals(draw.getNumberThreeDigits().getNumberInStringFormat().substring(1)))
        {
            result.add("Premye lo a sipoze menm ak loto 3 chif la");
            return  result;
        }

        return result;
    }


    public Result updateDraw(Draw draw, Long enterpriseId) {
        Result result = validateModel(draw);
        if (!result.isValid()){
            return result;
        }
        draw.setEnterprise(new Enterprise());
        draw.getEnterprise().setId(enterpriseId);
        List<Draw> savedDraw = drawRepository.findAllByShiftNameAndDrawDateAndEnterpriseId(draw.getShift().getName(), draw.getDrawDate(), enterpriseId);
        if (savedDraw!=null){
            for (Draw sd : savedDraw){
                if (sd.getId().longValue() != draw.getId().longValue()){
                    result.add("Tiraj sa egziste deja");
                    return result;
                }
            }
        }

        Draw currentDraw = drawRepository.findDrawByIdAndEnterpriseId(draw.getId(), enterpriseId);
        currentDraw.setModificationDate(new Date());
        currentDraw.setNumberThreeDigits(draw.getNumberThreeDigits());
        currentDraw.setFirstDraw(draw.getFirstDraw());
        currentDraw.setSecondDraw(draw.getSecondDraw());
        currentDraw.setThirdDraw(draw.getThirdDraw());
        currentDraw.setAmountLost(0.0);
        currentDraw.setAmountWon(0.0);
        try {
            determineWonTicket(currentDraw, true);
            currentDraw.setAmountLost(determineAmountLostForSoldTicket(currentDraw).getAmountLost());
            currentDraw.setAmountWon(currentDraw.getAmountSold() - currentDraw.getAmountLost());
            drawRepository.save(currentDraw);
        }catch (Exception ex){
            result.add("Tiraj la pa ka aktyalize reeseye ankò");
        }
        return  result;
    }

    public ArrayList<Draw> findAllDraw(Long entepriseId) {
        return (ArrayList<Draw>) drawRepository.findAllByEnterpriseIdOrderByIdDesc(entepriseId);
    }

    public Result deleteDrawById(Long id, Long entepriseId){
        Result result = new Result();
        Draw draw = drawRepository.findDrawByIdAndEnterpriseId(id, entepriseId);
        if(draw == null) {
            result.add("Tiraj sa ou bezwen elimine a pa egziste");
            return result;
        }
        try{
            draw.setAmountLost(0.0);
            draw.setAmountWon(0.0);
            cancelDraw(draw);
            draw.setEnterprise(null);
            draw.setNumberThreeDigits(null);
            draw.setFirstDraw(null);
            draw.setSecondDraw(null);
            draw.setThirdDraw(null);
            draw.setShift(null);
            drawRepository.save(draw);
            drawRepository.deleteById(draw.getId());
        }catch (Exception ex){
            result.add("Tiraj la pa ka anile reeseye ankò");
        }

        return result;
    }

    public List<Draw> findAllDraw(int page, int itemPerPage, Boolean state, int day, int month, int year, Long enterpriseId){

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
              return drawRepository.selectDrawByDayAndEnabledAndEnterpriseId(state, modificationDate, enterpriseId ,pageable);
           } else if (day > 0 && month > 0 && year <=0){
              return drawRepository.selectDrawByDayAndMonthAndEnabledAndEnterpriseId(state, modificationDate, enterpriseId ,pageable);
           } else if (day > 0 && month <= 0 && year >0){
              return drawRepository.selectDrawByDayAndYearEnabledAndEnterpriseId(state, modificationDate, enterpriseId ,pageable);
           } else if (day <= 0 && month > 0 && year <= 0){
              return drawRepository.selectDrawByMonthAndEnabledAndEnterpriseId(state, modificationDate, enterpriseId ,pageable);
           } else if (day <= 0 && month > 0 && year > 0){
              return drawRepository.selectDrawByMonthAndYearEnabledAndEnterpriseId(state, modificationDate, enterpriseId ,pageable);
           } else if (day <= 0 && month <= 0 && year >0){
              return drawRepository.selectDrawByYearAndEnabledAndEnterpriseId(state, modificationDate, enterpriseId ,pageable);
           } else if (day > 0 && month > 0 && year >0){
              return drawRepository.findAllByEnabledAndModificationDateAndEnterpriseId(state, modificationDate, enterpriseId, pageable);
           } else if (day < 0 && month < 0 && year < 0){
               return drawRepository.findAllByEnabledAndEnterpriseId (pageable, state, enterpriseId);
           }

        } else {
            if(day > 0 && month<=0 && year <=0) {
                return drawRepository.selectDrawByDayAndEnterpriseId(modificationDate, enterpriseId ,pageable);
            } else if (day > 0 && month > 0 && year <=0){
                return drawRepository.selectDrawByDayAndMonthAndEnterpriseId(modificationDate, enterpriseId ,pageable);
            } else if (day > 0 && month <= 0 && year >0){
                return drawRepository.selectDrawByDayAndYearAndEnterpriseId(modificationDate, enterpriseId ,pageable);
            } else if (day <= 0 && month > 0 && year <= 0){
                return drawRepository.selectDrawByMonthAndEnterpriseId(modificationDate, enterpriseId ,pageable);
            } else if (day <= 0 && month > 0 && year > 0){
                return drawRepository.selectDrawByMonthAndYearAndEnterpriseId(modificationDate, enterpriseId ,pageable);
            } else if (day <= 0 && month <= 0 && year >0){
                return drawRepository.selectDrawByYearAndEnterpriseId(modificationDate, enterpriseId ,pageable);
            } else if (day > 0 && month > 0 && year >0){
                return drawRepository.findAllByModificationDateAndEnterpriseId( modificationDate, enterpriseId ,pageable);
            }
        }
        return drawRepository.findAllByEnterpriseIdOrderByIdDesc(enterpriseId);
    }

    public Draw findDrawById(Long id, Long enterpriseId){

        return drawRepository.findDrawByIdAndEnterpriseId(id, enterpriseId);
    }

     public List<Draw> findAllDrawByEnabled(Boolean enabled, Long enterpriseId){
        if (enabled!= null){
            return drawRepository.findAllByEnabledAndEnterpriseIdOrderByIdDesc(enabled, enterpriseId);
        }
         return drawRepository.findAllByEnterpriseIdOrderByIdDesc(enterpriseId);
     }

     private Draw determineAmountForSoldTicket(Draw draw){
         List<Sale> sales =  getSales(draw);
         for (Sale sale: sales){
             draw.setAmountSold(draw.getAmountSold()+sale.getTotalAmount());
         }
         return  draw;
     }

    private Draw determineAmountLostForSoldTicket(Draw draw){
        List<Sale> sales =  getSales(draw);
        for (Sale sale: sales){
            if (sale.getTicket().isWon()){
                draw.setAmountLost(draw.getAmountLost()+ sale.getTicket().getAmountWon());
            }
        }
        return  draw;
    }
     public void determineWonTicket(Draw draw, boolean updating){
        List<CombinationType> combinationTypes = combinationTypeService.findallByEnterpriseId(draw.getEnterprise().getId());
         List<Sale> sales =  getSales(draw);

        if(!sales.isEmpty()) {
            List<DrawViewModel> drawViewModels = generateCombination(combinationTypes, draw);
            for (int i = 0; i < sales.size(); i++) {
                boolean won = false;
                double amoutWon = 0.0;
                for (int j = 0; j < sales.get(i).getSaleDetails().size(); j++) {
                    Pair<Double, Boolean> youwon = youWon(sales.get(i).getSaleDetails().get(j), drawViewModels);
                    if (youwon.getValue1()) {
                        sales.get(i).getSaleDetails().get(j).setWon(true);
                        won = true;
                        amoutWon += youwon.getValue0();
                    }
                }
                if (!updating){
                    if (won) {
                        sales.get(i).getTicket().setWon(won);
                        sales.get(i).getTicket().setAmountWon(amoutWon);
                        saleRepository.save(sales.get(i));
                    }
                }else {
                    if (won) {
                        sales.get(i).getTicket().setWon(won);
                        sales.get(i).getTicket().setAmountWon(amoutWon);
                        saleRepository.save(sales.get(i));
                    } else {
                        sales.get(i).getTicket().setWon(won);
                        sales.get(i).getTicket().setAmountWon(0.0);
                        for (int x = 0; x <sales.get(i).getSaleDetails().size(); x++){
                            sales.get(i).getSaleDetails().get(x).setWon(false);
                        }
                        saleRepository.save(sales.get(i));
                    }
                }
            }

        }
     }

     public void cancelDraw (Draw draw){
        List<CombinationType> combinationTypes = combinationTypeService.findallByEnterpriseId(draw.getEnterprise().getId());
         List<Sale> sales =  getSales(draw);

        if(!sales.isEmpty()) {
            List<DrawViewModel> drawViewModels = generateCombination(combinationTypes, draw);
            for (int i = 0; i < sales.size(); i++) {
                boolean won = false;
                for (int j = 0; j < sales.get(i).getSaleDetails().size(); j++) {
                    Pair<Double, Boolean> youwon = youWon(sales.get(i).getSaleDetails().get(j), drawViewModels);
                    if (youwon.getValue1()) {
                        sales.get(i).getSaleDetails().get(j).setWon(false);
                        won = true;
                    }
                }
                if (won) {
                    sales.get(i).getTicket().setWon(false);
                    sales.get(i).getTicket().setAmountWon(0.0);
                    saleRepository.save(sales.get(i));
                }
            }
        }
     }

     private Pair<Double, Boolean> youWon (SaleDetail saleDetail, List<DrawViewModel> drawViewModels){
        for (DrawViewModel drawViewModel : drawViewModels){
            if(drawViewModel.getCombination().equals(saleDetail.getCombination().getResultCombination())){
               return Pair.with (drawViewModel.getPayedPrice()*saleDetail.getPrice(), true);
            }
        }
        return Pair.with (0.0, false);
     }

     private  List<DrawViewModel> generateCombination (List<CombinationType> combinationTypes, Draw draw){
        List<DrawViewModel> drawViewModels = new ArrayList<>();
         for (CombinationType combinationType: combinationTypes){
             if (combinationType.getProducts().getName().equals(CombinationTypes.BOLET.name())){
                for (int i = 0; i<3; i++){
                    DrawViewModel drawViewModel = new DrawViewModel();
                    drawViewModel.setCombinationTypeId(CombinationTypes.BOLET.ordinal());
                    if (i == 0){
                        drawViewModel.setCombination(draw.getFirstDraw().getNumberInStringFormat());
                        drawViewModel.setPayedPrice(combinationType.getPayedPriceFirstDraw());
                    } else if (i == 1){
                    drawViewModel.setCombination(draw.getSecondDraw().getNumberInStringFormat());
                        drawViewModel.setPayedPrice(combinationType.getPayedPriceSecondDraw());
                    } else {
                    drawViewModel.setCombination(draw.getThirdDraw().getNumberInStringFormat());
                        drawViewModel.setPayedPrice(combinationType.getPayedPriceThirdDraw());
                    }
                    drawViewModels.add(drawViewModel);
                }
             } else if (combinationType.getProducts().getName().equals(CombinationTypes.LOTO_TWA_CHIF.name())){
                 DrawViewModel drawViewModel = generateDrawViewModel(CombinationTypes.LOTO_TWA_CHIF.ordinal(),combinationType.getPayedPrice(), null, null, false, draw);
                 drawViewModel.setCombination(draw.getNumberThreeDigits().getNumberInStringFormat());
                 drawViewModels.add(drawViewModel);

             } else if (combinationType.getProducts().getName().equals(CombinationTypes.LOTO_KAT_CHIF.name())){
                 drawViewModels.add(generateDrawViewModel(CombinationTypes.LOTO_KAT_CHIF.ordinal(),combinationType.getPayedPrice(), draw.getSecondDraw(), draw.getThirdDraw(), true, draw));
             } else if (combinationType.getProducts().getName().equals(CombinationTypes.OPSYON.name())){
                 for (int i=0 ; i<3; i++) {
                   if (i == 0){
                       drawViewModels.add(generateDrawViewModel(CombinationTypes.OPSYON.ordinal(),combinationType.getPayedPrice(), draw.getFirstDraw(), draw.getSecondDraw(), true, draw));
                   } else if (i == 1){
                       drawViewModels.add(generateDrawViewModel(CombinationTypes.OPSYON.ordinal(),combinationType.getPayedPrice(), draw.getFirstDraw(), draw.getThirdDraw(), true, draw));
                   } else {
                       drawViewModels.add(generateDrawViewModel(CombinationTypes.OPSYON.ordinal(),combinationType.getPayedPrice(), draw.getSecondDraw(), draw.getThirdDraw(), true, draw));
                   }
                 }

             } else if (combinationType.getProducts().getName().equals(CombinationTypes.MARYAJ.name())){
                 for (int i=0 ; i<3; i++) {
                     if (i == 0){
                         drawViewModels.add(generateDrawViewModel(CombinationTypes.MARYAJ.ordinal(),combinationType.getPayedPrice(), draw.getFirstDraw(), draw.getSecondDraw(), true, draw));
                         drawViewModels.add(generateDrawViewModel(CombinationTypes.MARYAJ.ordinal(),combinationType.getPayedPrice(), draw.getSecondDraw(), draw.getFirstDraw(), true, draw));
                     } else if (i == 1){
                         drawViewModels.add(generateDrawViewModel(CombinationTypes.MARYAJ.ordinal(),combinationType.getPayedPrice(), draw.getFirstDraw(), draw.getThirdDraw(), true, draw));
                         drawViewModels.add(generateDrawViewModel(CombinationTypes.MARYAJ.ordinal(),combinationType.getPayedPrice(), draw.getThirdDraw(), draw.getFirstDraw(), true, draw));
                     } else {
                         drawViewModels.add(generateDrawViewModel(CombinationTypes.MARYAJ.ordinal(),combinationType.getPayedPrice(), draw.getSecondDraw(), draw.getThirdDraw(), true, draw));
                         drawViewModels.add(generateDrawViewModel(CombinationTypes.MARYAJ.ordinal(),combinationType.getPayedPrice(), draw.getThirdDraw(), draw.getSecondDraw(), true, draw));
                     }
                 }

             } else if (combinationType.getProducts().getName().equals(CombinationTypes.EXTRA.name())){
                 for (int i = 0 ; i<3; i++) {
                     if (i == 0){
                         drawViewModels.add(generateDrawViewModel(CombinationTypes.EXTRA.ordinal(),combinationType.getPayedPrice(), draw.getSecondDraw(), draw.getFirstDraw(), true, draw));
                     } else if (i == 1){
                         drawViewModels.add(generateDrawViewModel(CombinationTypes.EXTRA.ordinal(),combinationType.getPayedPrice(), draw.getThirdDraw(), draw.getFirstDraw(), true, draw));
                     } else {
                         DrawViewModel drawViewModel = generateDrawViewModel(CombinationTypes.EXTRA.ordinal(),combinationType.getPayedPrice(), null, null, false, draw);
                         drawViewModel.setCombination(draw.getFirstDraw().getNumberInStringFormat().substring(1)+ draw.getSecondDraw().getNumberInStringFormat()+" "+draw.getThirdDraw().getNumberInStringFormat());
                         drawViewModels.add(drawViewModel);
                     }
                 }
             }
         }
         return drawViewModels;
     }

     private DrawViewModel generateDrawViewModel(int type, double price, NumberTwoDigits pos1, NumberTwoDigits  pos2, boolean setCombination, Draw draw) {
        DrawViewModel drawViewModel=  new DrawViewModel();
        drawViewModel.setPayedPrice(price);
        drawViewModel.setCombinationTypeId(type);
        if (setCombination){
            if (type == CombinationTypes.MARYAJ.ordinal()){
                drawViewModel.setCombination(pos1.getNumberInStringFormat()+ "x"+pos2.getNumberInStringFormat());
            } else if (type == CombinationTypes.EXTRA.ordinal()){
                drawViewModel.setCombination(draw.getNumberThreeDigits().getNumberInStringFormat()+ " "+pos1.getNumberInStringFormat());
            }  if (type == CombinationTypes.OPSYON.ordinal() || type == CombinationTypes.LOTO_KAT_CHIF.ordinal() ){
                drawViewModel.setCombination(pos1.getNumberInStringFormat()+ " "+pos2.getNumberInStringFormat());
            }
        }
        return  drawViewModel;
     }

     private  List<Sale> getSales(Draw draw) {
         Pair<Date, Date> startAndEndDate = null;
         if (draw.getShift().getName().equals(Shifts.Maten.name())){
             Shift shift = shiftRepository.findShiftByNameAndEnterpriseId(Shifts.New_York.name(), draw.getEnterprise().getId());
             if (shift != null) {
                 startAndEndDate = Helper.getStartDateAndEndDate(shift.getCloseTime(),
                         draw.getShift().getCloseTime(), draw.getDrawDate(), -1, "dd/MM/yyyy, hh:mm:ss aa");
             }
         } else {
             Shift shift = shiftRepository.findShiftByNameAndEnterpriseId(Shifts.Maten.name(), draw.getEnterprise().getId());
             if (shift != null) {
                 startAndEndDate = Helper.getStartDateAndEndDate(shift.getCloseTime(),
                         draw.getShift().getCloseTime(), draw.getDrawDate(), 0, "dd/MM/yyyy, hh:mm:ss aa");
             }

         }
             return saleRepository.selectAllSale(draw.getEnterprise().getId(), draw.getShift().getId() ,startAndEndDate.getValue0() , startAndEndDate.getValue1());

     }
}
