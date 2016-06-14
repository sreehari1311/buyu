package com.buyu;
import com.buyu.matcher.PrintMatchListener;
import com.buyu.utils.common.BuyuConfigurator;

import no.priv.garshol.duke.ConfigLoader;
import no.priv.garshol.duke.Configuration;
import no.priv.garshol.duke.Processor;
 
public class RunDuke {

  public static void main(String[] argv) throws Exception {
    Configuration config =
        ConfigLoader
            .load(BuyuConfigurator.getInstance().getValue("DUKE_TITLE_CONFIG"));
     
    Processor proc = new Processor(config);
    PrintMatchListener matchListener = new PrintMatchListener(true, true, true, false, config.getProperties(),
            true);
    proc.addMatchListener(matchListener);
    proc.setPerformanceProfiling(true);
    
    proc.link();
     
     
    proc.close();
    System.out.println("Total Matches is::::"+matchListener.getMatches());
  }

}