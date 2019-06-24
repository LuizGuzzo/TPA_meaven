package _my_tools;

import java.io.File;
import java.io.IOException;

public class GnuPlotter {
	private String template;
	private String script_plot;
	
	public GnuPlotter(String t){
		template = t;	
		script_plot = null;
	}
	
	public String monta_script(String params){
		String []args = params.split(";");
		String arg;
		script_plot = new String(template);
		
		for(int i=0; i < args.length; i++){
			arg = "@" + String.valueOf(i+1);
			script_plot = script_plot.replace(arg, args[i]);
		}
		return script_plot;
	}
	
	public String executa_script(){
		/* Salva o script mathplotlib para execução. */
		ArquivoTxt arqplot = ArquivoTxt.open("./src/_my_tools/tmp.plot", "wt");
		arqplot.write(script_plot);
		arqplot.close();
		
		try {
			String r = System.getProperty("user.dir") + "";
			String []s = {"gnuplot", "tmp.plot"};
		    Runtime rt = Runtime.getRuntime();
		    Process proc = rt.exec(s, null, new File(r));
		    proc.waitFor();
		} catch (InterruptedException | IOException e) {
		    System.err.println("Fail: " + e);
		}
        
		return script_plot;
	}
	
	public String salva_script(String nome_arq){
		ArquivoTxt arqplot = ArquivoTxt.open(nome_arq, "wt");
		arqplot.write(script_plot);
		arqplot.close();
		
		return nome_arq;
	}
} // fim GnuPlotter
