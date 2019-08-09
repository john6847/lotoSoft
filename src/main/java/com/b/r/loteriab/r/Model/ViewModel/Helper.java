package com.b.r.loteriab.r.Model.ViewModel;

import com.b.r.loteriab.r.Model.Bank;
import com.b.r.loteriab.r.Model.Enterprise;
import com.b.r.loteriab.r.Repository.BankRepository;
import com.b.r.loteriab.r.Repository.EnterpriseRepository;
import com.b.r.loteriab.r.Repository.TicketRepository;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class Helper {

    @Autowired
    private EnterpriseRepository enterpriseRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private BankRepository bankRepository;

    public Pair<String, Integer> createNewEnterpriseIdentifier () {
        if (enterpriseRepository.findAll().size() <= 0){
            return Pair.with("BR-00001", 1);
        }
        int sequence = enterpriseRepository.selectMaxSequence();
        sequence += 1;
        if (sequence <= 9){
            return Pair.with("BR-0000"+sequence, sequence);
        } else if (sequence <= 99){
            return Pair.with("BR-000"+sequence, sequence);
        }else if (sequence <= 999){
            return Pair.with("BR-00"+sequence, sequence);
        }else if (sequence <= 9999){
            return Pair.with("BR-0"+sequence, sequence);
        }else{
            return Pair.with("BR-"+sequence, sequence);
        }
    }

    public Pair<String, Long> createNewTicketSerial (Enterprise enterprise) {
        String zeros = "000000000000000";
        if (ticketRepository.findAllByEnterpriseId(enterprise.getId()).size() <= 0){
            return Pair.with("BR-"+enterprise.getName().substring(0,2).toUpperCase()+"000000000000001", Long.valueOf(1));
        }

        long sequence = ticketRepository.selectMaxSequence(enterprise);
        sequence += 1;
        String sequenceString = String.valueOf(sequence);
        String nextSequence = "BR-"+enterprise.getName().substring(0,2).toUpperCase()+
                zeros.substring(0, (zeros.length()- sequenceString.length()))+ sequenceString;
        return Pair.with(nextSequence, sequence);

    }

    public String createBankSerial(Enterprise enterprise) {
        if (bankRepository.findAllByEnterpriseId(enterprise.getId()).size() <= 0){
            return "BR-"+ enterprise.getName().substring(0,2).toUpperCase() + "-"+ enterprise.getId()+"-00000";
        }
        String zeros = "00000";
        Bank bank = bankRepository.findTopByEnterpriseIdOrderByEnterpriseIdDesc(enterprise.getId());
        int serialLength= bank.getSerial().length();
        int nextValue = Integer.valueOf(bank.getSerial().substring(serialLength-5, serialLength)) +  1 ;
        return "BR-"+ enterprise.getName().substring(0,2).toUpperCase() + "-"+ enterprise.getId()+ "-" + zeros.substring(0, 5 - String.valueOf(nextValue).length()) +""+ nextValue;
    }

    public String replace (String text, String old, String newChar) {
        return text.replace(old, newChar);
    }

    public String upper (String text) {
        return text.toUpperCase();
    }

    public String lower (String text) {
        return text.toLowerCase();
    }

}
