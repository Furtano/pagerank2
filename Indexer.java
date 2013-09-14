package Classen_Beleg_1;

import java.io.File;
import java.io.IOException;
import java.util.List;

import lombok.Data;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
@Data
public class Indexer {
	private String rootUrl;
	private static final String outputFile = "indexer";
	private IndexWriter writer = null;
	private static final int maxNumberOfEntries = 10;
	private static final String contentsField = "contents";
	private static final String nameField = "name";

	@SuppressWarnings("deprecation")
	public Indexer(String rootUrl) throws CorruptIndexException, LockObtainFailedException, IOException {
		this.rootUrl = rootUrl;
		
			Directory dir = FSDirectory.open(new File(outputFile));
			this.writer = new IndexWriter(dir, new StandardAnalyzer(
					Version.LUCENE_36), true,
					IndexWriter.MaxFieldLength.UNLIMITED);
		
	}

	public void addToIndex(Node n) throws IOException {
		addDocument(n);
	}

	private void addDocument(Node n) throws IOException {
		Document doc = buildIndexedDocument(n);
		writer.addDocument(doc);
	}

	private Document buildIndexedDocument(Node n) throws IOException {
		Document doc = new Document();
		doc.add(new Field(contentsField, n.getContent(), Field.Store.YES,
				Field.Index.ANALYZED));
		doc.add(new Field(nameField, n.getName(), Field.Store.YES,
				Field.Index.NOT_ANALYZED));

		return doc;
	}

	public void close() throws IOException {
		this.writer.close();
	}

	public IndexerItem[] search(String value) throws IOException,
			ParseException {
		Directory dir = FSDirectory.open(new File(outputFile));
		@SuppressWarnings("deprecation")
		IndexSearcher is = new IndexSearcher(dir);
		QueryParser parser = new QueryParser(Version.LUCENE_36, contentsField,
				new StandardAnalyzer(Version.LUCENE_36));
		Query query = parser.parse(value);
		long start = System.currentTimeMillis();
		TopDocs hits = is.search(query, maxNumberOfEntries);
		long end = System.currentTimeMillis();
		System.out.println("Found " + hits.totalHits + " document(s) (in "
				+ (end - start) + " milliseconds) that matched query '" + value
				+ "':");

		IndexerItem[] items = new IndexerItem[hits.totalHits];
		int index = 0;
		for (ScoreDoc sd : hits.scoreDocs) {
			Document doc = is.doc(sd.doc);
			IndexerItem item = new IndexerItem(doc.get(nameField), sd.score);
			items[index] = item;
			index++;
		}
		is.close();

		return items;
	}
}
