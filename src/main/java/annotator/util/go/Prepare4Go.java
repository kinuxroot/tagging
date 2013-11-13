package annotator.util.go;

import java.io.File;
import java.util.ArrayList;

/**
 * @author Brandon B. Lin
 * 
 */
public class Prepare4Go {

	public static final String PATHTOSAVEGOCSV = "go"+ File.separator+"go_csv";
	public static final String PATHTOUNZIP = "go" + File.separator+"go_annotation_file";
	public static ArrayList<String> SPECIES = new ArrayList<String>();
	//[Agrobacterium_tumefaciens, Arabidopsis_thaliana, Aspergillus_nidulans, Bos_taurus, Caenorhabditis_elegans, Candida_albicans, Canis_lupus_familiaris, Danio_rerio, Dickeya_dadantii, Dictyostelium_discoideum, Drosophila_melanogaster, Escherichia_coli, Gallus_gallus, gene_association.PAMGO_Atumefaciens, Homo_sapiens, Leishmania_major, Magnaporthe_grisea, Microbial_multispecies, Mus_musculus, Oomycetes, Oryza_sativa, Plasmodium_falciparum, Pseudomonas_aeruginosa, Rattus_norvegicus, Reactome_multispecies, Saccharomyces_cerevisiae, Schizosaccharomyces_pombe, Solanaceae, Sus_scrofa, Trypanosoma_brucei, UniProt_multispecies]

	/**
	 * Download all of the filtered annotation files from :
	 * http://www.geneontology.org/GO.downloads.annotations.shtml
	 */
	public static void downloadAnnotatonFile() {

	}

	public static void unZipAndSaveAsCSV(String folderContainZipFiles,
			String folderToSaveCSV) {
		File zipFolder = new File(folderContainZipFiles);
		String[] zipsName = zipFolder.list();//include extension
		for (String name : zipsName) {
			File zipFile = new File(zipFolder, name); // file to be unziped
			String temp = name.substring(0, name.length() - 3); // remove ".gz"
			SPECIES.add(temp);
			
			File unZipFile = new File(new File(PATHTOUNZIP), temp); // unziped file
			UnZip.gunzipIt(zipFile.getAbsolutePath(), unZipFile.getAbsolutePath());
			Trans2CSV.transfer2Csv(unZipFile, folderToSaveCSV);
		}

	}
	

}
