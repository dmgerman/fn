begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.imports
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|imports
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|*
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|*
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|*
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|ActionListener
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|parsers
operator|.
name|SAXParserFactory
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|parsers
operator|.
name|SAXParser
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|ActionEvent
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Pattern
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Matcher
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|*
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|undo
operator|.
name|NamedCompound
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|undo
operator|.
name|UndoableInsertEntry
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|*
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|HelpAction
import|;
end_import

begin_comment
comment|/**  *<p>Title:</p>  *<p>Description:</p>  *<p>Copyright: Copyright (c) 2003</p>  *<p>Company:</p>  * @author not attributable  * @version 1.0  */
end_comment

begin_class
DECL|class|MedlineFetcher
specifier|public
class|class
name|MedlineFetcher
extends|extends
name|SidePaneComponent
implements|implements
name|Runnable
block|{
comment|/**@class SearchResult      *        nested class.      */
DECL|class|SearchResult
specifier|public
class|class
name|SearchResult
block|{
DECL|field|count
specifier|public
name|int
name|count
decl_stmt|;
DECL|field|retmax
specifier|public
name|int
name|retmax
decl_stmt|;
DECL|field|retstart
specifier|public
name|int
name|retstart
decl_stmt|;
DECL|field|ids
specifier|public
name|String
name|ids
init|=
literal|""
decl_stmt|;
DECL|field|idList
specifier|public
name|ArrayList
name|idList
init|=
operator|new
name|ArrayList
argument_list|()
decl_stmt|;
DECL|method|SearchResult ()
specifier|public
name|SearchResult
parameter_list|()
block|{
name|count
operator|=
literal|0
expr_stmt|;
name|retmax
operator|=
literal|0
expr_stmt|;
name|retstart
operator|=
literal|0
expr_stmt|;
block|}
DECL|method|addID (String id)
specifier|public
name|void
name|addID
parameter_list|(
name|String
name|id
parameter_list|)
block|{
name|idList
operator|.
name|add
argument_list|(
name|id
argument_list|)
expr_stmt|;
if|if
condition|(
name|ids
operator|!=
literal|""
condition|)
name|ids
operator|+=
literal|","
operator|+
name|id
expr_stmt|;
else|else
name|ids
operator|=
name|id
expr_stmt|;
block|}
block|}
DECL|field|PACING
specifier|final
name|int
name|PACING
init|=
literal|20
decl_stmt|;
DECL|field|MAX_TO_FETCH
specifier|final
name|int
name|MAX_TO_FETCH
init|=
literal|10
decl_stmt|;
DECL|field|idList
name|String
name|idList
decl_stmt|;
DECL|field|tf
name|JTextField
name|tf
init|=
operator|new
name|JTextField
argument_list|()
decl_stmt|;
DECL|field|pan
name|JPanel
name|pan
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
DECL|field|gbl
name|GridBagLayout
name|gbl
init|=
operator|new
name|GridBagLayout
argument_list|()
decl_stmt|;
DECL|field|con
name|GridBagConstraints
name|con
init|=
operator|new
name|GridBagConstraints
argument_list|()
decl_stmt|;
DECL|field|ths
name|MedlineFetcher
name|ths
init|=
name|this
decl_stmt|;
DECL|field|authorDialog
name|AuthorDialog
name|authorDialog
decl_stmt|;
DECL|field|jFrame
name|JFrame
name|jFrame
decl_stmt|;
comment|// invisible dialog holder
DECL|field|go
name|JButton
name|go
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Fetch"
argument_list|)
argument_list|)
decl_stmt|,
DECL|field|helpBut
name|helpBut
init|=
operator|new
name|JButton
argument_list|(
operator|new
name|ImageIcon
argument_list|(
name|GUIGlobals
operator|.
name|helpIconFile
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|help
name|HelpAction
name|help
decl_stmt|;
DECL|method|MedlineFetcher (SidePaneManager p0)
specifier|public
name|MedlineFetcher
parameter_list|(
name|SidePaneManager
name|p0
parameter_list|)
block|{
name|super
argument_list|(
name|p0
argument_list|,
name|GUIGlobals
operator|.
name|fetchMedlineIcon
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Fetch Medline"
argument_list|)
argument_list|)
expr_stmt|;
name|help
operator|=
operator|new
name|HelpAction
argument_list|(
name|Globals
operator|.
name|helpDiag
argument_list|,
name|GUIGlobals
operator|.
name|medlineHelp
argument_list|,
literal|"Help"
argument_list|)
expr_stmt|;
name|helpBut
operator|.
name|addActionListener
argument_list|(
name|help
argument_list|)
expr_stmt|;
name|helpBut
operator|.
name|setMargin
argument_list|(
operator|new
name|Insets
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|)
argument_list|)
expr_stmt|;
comment|//tf.setMinimumSize(new Dimension(1,1));
comment|//add(hd, BorderLayout.NORTH);
comment|//ok.setToolTipText(Globals.lang("Fetch Medline"));
name|JPanel
name|main
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|main
operator|.
name|setLayout
argument_list|(
name|gbl
argument_list|)
expr_stmt|;
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|BOTH
expr_stmt|;
comment|//con.insets = new Insets(0, 0, 2,  0);
name|con
operator|.
name|gridwidth
operator|=
name|GridBagConstraints
operator|.
name|REMAINDER
expr_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|weighty
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|BOTH
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|tf
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|main
operator|.
name|add
argument_list|(
name|tf
argument_list|)
expr_stmt|;
name|con
operator|.
name|weighty
operator|=
literal|0
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
literal|1
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|go
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|main
operator|.
name|add
argument_list|(
name|go
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
name|GridBagConstraints
operator|.
name|REMAINDER
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|helpBut
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|main
operator|.
name|add
argument_list|(
name|helpBut
argument_list|)
expr_stmt|;
name|ActionListener
name|listener
init|=
operator|new
name|ActionListener
argument_list|()
block|{
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
operator|(
operator|new
name|Thread
argument_list|(
name|ths
argument_list|)
operator|)
operator|.
name|start
argument_list|()
expr_stmt|;
comment|// Run fetch in thread.
block|}
block|}
decl_stmt|;
name|main
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEmptyBorder
argument_list|(
literal|1
argument_list|,
literal|1
argument_list|,
literal|1
argument_list|,
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|main
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|go
operator|.
name|addActionListener
argument_list|(
name|listener
argument_list|)
expr_stmt|;
name|tf
operator|.
name|addActionListener
argument_list|(
name|listener
argument_list|)
expr_stmt|;
block|}
DECL|method|getTextField ()
specifier|public
name|JTextField
name|getTextField
parameter_list|()
block|{
return|return
name|tf
return|;
block|}
DECL|method|fetchById ()
specifier|public
name|void
name|fetchById
parameter_list|()
block|{
comment|//if(idList==null || idList.trim().equals(""))//if user pressed cancel
comment|//  return;
name|Pattern
name|p
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"\\d+[,\\d+]*"
argument_list|)
decl_stmt|;
comment|//System.out.println(""+p+"\t"+idList);
name|Matcher
name|m
init|=
name|p
operator|.
name|matcher
argument_list|(
name|idList
argument_list|)
decl_stmt|;
if|if
condition|(
name|m
operator|.
name|matches
argument_list|()
condition|)
block|{
name|panel
operator|.
name|frame
argument_list|()
operator|.
name|output
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Fetching Medline by ID..."
argument_list|)
argument_list|)
expr_stmt|;
name|ArrayList
name|bibs
init|=
name|fetchMedline
argument_list|(
name|idList
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|bibs
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|bibs
operator|.
name|size
argument_list|()
operator|>
literal|0
operator|)
condition|)
block|{
comment|//if (panel.prefs().getBoolean("useOwner")) {
comment|//    Util.setDefaultOwner(bibs, panel.prefs().get("defaultOwner"));
comment|//}
name|tf
operator|.
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
comment|/*NamedCompound ce = new NamedCompound("fetch Medline"); 		Iterator i = bibs.iterator(); 		while (i.hasNext()) { 		    try { 			BibtexEntry be = (BibtexEntry) i.next(); 			String id = Util.createId(be.getType(), panel.database()); 			be.setId(id); 			entries.add(be); 			//panel.database().insertEntry(be); 			//ce.addEdit(new UndoableInsertEntry(panel.database(), be, panel)); 		    } 		    catch (KeyCollisionException ex) { 		    } 		    }*/
comment|//ce.end();
name|panel
operator|.
name|frame
argument_list|()
operator|.
name|addBibEntries
argument_list|(
name|bibs
argument_list|,
literal|null
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|panel
operator|.
name|markBaseChanged
argument_list|()
expr_stmt|;
name|panel
operator|.
name|refreshTable
argument_list|()
expr_stmt|;
if|if
condition|(
name|bibs
operator|.
name|size
argument_list|()
operator|>
literal|0
condition|)
block|{
name|BibtexEntry
index|[]
name|entries
init|=
operator|(
name|BibtexEntry
index|[]
operator|)
name|bibs
operator|.
name|toArray
argument_list|(
operator|new
name|BibtexEntry
index|[
literal|0
index|]
argument_list|)
decl_stmt|;
name|panel
operator|.
name|selectEntries
argument_list|(
name|entries
argument_list|,
literal|0
argument_list|)
expr_stmt|;
if|if
condition|(
name|entries
operator|.
name|length
operator|==
literal|1
condition|)
name|panel
operator|.
name|showEntry
argument_list|(
name|entries
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
comment|//else
comment|//    panel.updateViewToSelected();
block|}
name|panel
operator|.
name|output
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Medline entries fetched"
argument_list|)
operator|+
literal|": "
operator|+
name|bibs
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
comment|//panel.undoManager.addEdit(ce);
block|}
else|else
name|panel
operator|.
name|output
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"No Medline entries found."
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|panel
operator|.
name|frame
argument_list|()
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Please enter a semicolon or comma separated list of Medline IDs (numbers)."
argument_list|)
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Input error"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
block|}
block|}
comment|//==================================================
comment|//
comment|//==================================================
DECL|method|fetchMedline (String id)
specifier|public
name|ArrayList
name|fetchMedline
parameter_list|(
name|String
name|id
parameter_list|)
block|{
name|ArrayList
name|bibItems
init|=
literal|null
decl_stmt|;
try|try
block|{
name|String
name|baseUrl
init|=
literal|"http://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=pubmed&retmode=xml&rettype=citation&id="
operator|+
name|id
decl_stmt|;
name|URL
name|url
init|=
operator|new
name|URL
argument_list|(
name|baseUrl
argument_list|)
decl_stmt|;
name|HttpURLConnection
name|data
init|=
operator|(
name|HttpURLConnection
operator|)
name|url
operator|.
name|openConnection
argument_list|()
decl_stmt|;
comment|/* Reader un = new InputStreamReader(data.getInputStream());         int c;         while ((c=un.read()) != -1) {           System.out.print((char)c);         }*/
comment|// Obtain a factory object for creating SAX parsers
name|SAXParserFactory
name|parserFactory
init|=
name|SAXParserFactory
operator|.
name|newInstance
argument_list|()
decl_stmt|;
comment|// Configure the factory object to specify attributes of the parsers it creates
name|parserFactory
operator|.
name|setValidating
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|parserFactory
operator|.
name|setNamespaceAware
argument_list|(
literal|true
argument_list|)
expr_stmt|;
comment|// Now create a SAXParser object
name|SAXParser
name|parser
init|=
name|parserFactory
operator|.
name|newSAXParser
argument_list|()
decl_stmt|;
comment|//May throw exceptions
name|MedlineHandler
name|handler
init|=
operator|new
name|MedlineHandler
argument_list|()
decl_stmt|;
comment|// Start the parser. It reads the file and calls methods of the handler.
name|parser
operator|.
name|parse
argument_list|(
name|data
operator|.
name|getInputStream
argument_list|()
argument_list|,
name|handler
argument_list|)
expr_stmt|;
comment|/*FileOutputStream out = new FileOutputStream(new File("/home/alver/ut.txt")); 	System.out.println("#####"); 	InputStream is = data.getInputStream(); 	int c; 	while ((c = is.read()) != -1) { 	    out.write((char)c); 	} 	System.out.println("#####"); 	out.close();*/
comment|// When you're done, report the results stored by your handler object
name|bibItems
operator|=
name|handler
operator|.
name|getItems
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|javax
operator|.
name|xml
operator|.
name|parsers
operator|.
name|ParserConfigurationException
name|e1
parameter_list|)
block|{}
catch|catch
parameter_list|(
name|org
operator|.
name|xml
operator|.
name|sax
operator|.
name|SAXException
name|e2
parameter_list|)
block|{}
catch|catch
parameter_list|(
name|java
operator|.
name|io
operator|.
name|IOException
name|e3
parameter_list|)
block|{}
return|return
name|bibItems
return|;
block|}
DECL|method|run ()
specifier|public
name|void
name|run
parameter_list|()
block|{
name|idList
operator|=
name|tf
operator|.
name|getText
argument_list|()
operator|.
name|replace
argument_list|(
literal|';'
argument_list|,
literal|','
argument_list|)
expr_stmt|;
comment|//if(idList==null || idList.trim().equals(""))//if user pressed cancel
comment|//    return;
name|Pattern
name|p1
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"\\d+[,\\d+]*"
argument_list|)
decl_stmt|,
name|p2
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|".+[,.+]*"
argument_list|)
decl_stmt|;
name|Matcher
name|m1
init|=
name|p1
operator|.
name|matcher
argument_list|(
name|idList
argument_list|)
decl_stmt|,
name|m2
init|=
name|p2
operator|.
name|matcher
argument_list|(
name|idList
argument_list|)
decl_stmt|;
if|if
condition|(
name|m1
operator|.
name|matches
argument_list|()
condition|)
block|{
name|panel
operator|.
name|frame
argument_list|()
operator|.
name|output
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Fetching Medline by id ..."
argument_list|)
argument_list|)
expr_stmt|;
name|idList
operator|=
name|tf
operator|.
name|getText
argument_list|()
operator|.
name|replace
argument_list|(
literal|';'
argument_list|,
literal|','
argument_list|)
expr_stmt|;
name|fetchById
argument_list|()
expr_stmt|;
comment|//System.out.println("Fetch by id");
block|}
elseif|else
if|if
condition|(
name|m2
operator|.
name|matches
argument_list|()
condition|)
block|{
name|panel
operator|.
name|frame
argument_list|()
operator|.
name|output
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Fetching Medline by term ..."
argument_list|)
argument_list|)
expr_stmt|;
comment|// my stuff
comment|//---------------------------
name|String
name|searchTerm
init|=
name|setupTerm
argument_list|(
name|idList
argument_list|)
decl_stmt|;
comment|// fix the syntax
name|SearchResult
name|result
init|=
name|getIds
argument_list|(
name|searchTerm
argument_list|,
literal|0
argument_list|,
literal|1
argument_list|)
decl_stmt|;
comment|// get the ids from entrez
comment|// prompt the user to number articles to retrieve
if|if
condition|(
name|result
operator|.
name|count
operator|==
literal|0
condition|)
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|panel
operator|.
name|frame
argument_list|()
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"No references found"
argument_list|)
argument_list|)
expr_stmt|;
return|return;
block|}
name|String
name|question
init|=
name|Globals
operator|.
name|lang
argument_list|(
literal|"References found"
argument_list|)
operator|+
literal|": "
operator|+
name|Integer
operator|.
name|toString
argument_list|(
name|result
operator|.
name|count
argument_list|)
operator|+
literal|"  "
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"Number of references to fetch?"
argument_list|)
decl_stmt|;
name|String
name|strCount
init|=
name|JOptionPane
operator|.
name|showInputDialog
argument_list|(
name|question
argument_list|,
name|Integer
operator|.
name|toString
argument_list|(
name|result
operator|.
name|count
argument_list|)
argument_list|)
decl_stmt|;
comment|// for strCount ...
if|if
condition|(
name|strCount
operator|==
literal|""
condition|)
return|return;
name|int
name|count
decl_stmt|;
try|try
block|{
name|count
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|strCount
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|ex
parameter_list|)
block|{
name|panel
operator|.
name|output
argument_list|(
literal|""
argument_list|)
expr_stmt|;
return|return;
block|}
for|for
control|(
name|int
name|jj
init|=
literal|0
init|;
name|jj
operator|<
name|count
condition|;
name|jj
operator|+=
name|PACING
control|)
block|{
comment|// get the ids from entrez
name|result
operator|=
name|getIds
argument_list|(
name|searchTerm
argument_list|,
name|jj
argument_list|,
name|PACING
argument_list|)
expr_stmt|;
name|String
index|[]
name|test
init|=
name|getTitles
argument_list|(
operator|(
name|String
index|[]
operator|)
name|result
operator|.
name|idList
operator|.
name|toArray
argument_list|(
operator|new
name|String
index|[
literal|0
index|]
argument_list|)
argument_list|)
decl_stmt|;
for|for
control|(
name|int
name|pelle
init|=
literal|0
init|;
name|pelle
operator|<
name|test
operator|.
name|length
condition|;
name|pelle
operator|++
control|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|": "
operator|+
name|test
index|[
name|pelle
index|]
argument_list|)
expr_stmt|;
block|}
comment|//System.out.println("fetching: " + result.ids);
name|ArrayList
name|bibs
init|=
name|fetchMedline
argument_list|(
name|result
operator|.
name|ids
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|bibs
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|bibs
operator|.
name|size
argument_list|()
operator|>
literal|0
operator|)
condition|)
block|{
name|tf
operator|.
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
comment|/*NamedCompound ce = new NamedCompound("fetch Medline"); 		    Iterator i = bibs.iterator(); 		    while (i.hasNext()) { 			try { 			    BibtexEntry be = (BibtexEntry) i.next(); 			    String id = Util.createId(be.getType(), panel.database()); 			    be.setId(id); 			    panel.database().insertEntry(be); 			    ce.addEdit(new UndoableInsertEntry(panel.database(), be, panel)); 			} 			catch (KeyCollisionException ex) { 			} 		    } 		    ce.end();*/
name|panel
operator|.
name|frame
argument_list|()
operator|.
name|addBibEntries
argument_list|(
name|bibs
argument_list|,
literal|null
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|panel
operator|.
name|output
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Medline entries fetched"
argument_list|)
operator|+
literal|": "
operator|+
name|bibs
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|BibtexEntry
index|[]
name|entries
init|=
operator|(
name|BibtexEntry
index|[]
operator|)
name|bibs
operator|.
name|toArray
argument_list|(
operator|new
name|BibtexEntry
index|[
literal|0
index|]
argument_list|)
decl_stmt|;
name|panel
operator|.
name|selectEntries
argument_list|(
name|entries
argument_list|,
literal|0
argument_list|)
expr_stmt|;
if|if
condition|(
name|entries
operator|.
name|length
operator|==
literal|1
condition|)
name|panel
operator|.
name|showEntry
argument_list|(
name|entries
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
comment|//panel.undoManager.addEdit(ce);
name|panel
operator|.
name|markBaseChanged
argument_list|()
expr_stmt|;
name|panel
operator|.
name|refreshTable
argument_list|()
expr_stmt|;
block|}
else|else
name|panel
operator|.
name|output
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"No Medline entries found."
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
DECL|method|setupTerm (String in)
specifier|public
name|String
name|setupTerm
parameter_list|(
name|String
name|in
parameter_list|)
block|{
name|Pattern
name|part1
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|", "
argument_list|)
decl_stmt|;
name|Pattern
name|part2
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|","
argument_list|)
decl_stmt|;
name|Pattern
name|part3
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|" "
argument_list|)
decl_stmt|;
name|Matcher
name|matcher
decl_stmt|;
name|matcher
operator|=
name|part1
operator|.
name|matcher
argument_list|(
name|in
argument_list|)
expr_stmt|;
name|in
operator|=
name|matcher
operator|.
name|replaceAll
argument_list|(
literal|"\\+AND\\+"
argument_list|)
expr_stmt|;
name|matcher
operator|=
name|part2
operator|.
name|matcher
argument_list|(
name|in
argument_list|)
expr_stmt|;
name|in
operator|=
name|matcher
operator|.
name|replaceAll
argument_list|(
literal|"\\+AND\\+"
argument_list|)
expr_stmt|;
name|matcher
operator|=
name|part3
operator|.
name|matcher
argument_list|(
name|in
argument_list|)
expr_stmt|;
name|in
operator|=
name|matcher
operator|.
name|replaceAll
argument_list|(
literal|"+"
argument_list|)
expr_stmt|;
return|return
name|in
return|;
block|}
comment|// this gets the initial list of ids
DECL|method|getIds (String term, int start,int pacing)
specifier|public
name|SearchResult
name|getIds
parameter_list|(
name|String
name|term
parameter_list|,
name|int
name|start
parameter_list|,
name|int
name|pacing
parameter_list|)
block|{
name|String
name|baseUrl
init|=
literal|"http://eutils.ncbi.nlm.nih.gov/entrez/eutils"
decl_stmt|;
name|String
name|medlineUrl
init|=
name|baseUrl
operator|+
literal|"/esearch.fcgi?db=pubmed&retmax="
operator|+
name|Integer
operator|.
name|toString
argument_list|(
name|pacing
argument_list|)
operator|+
literal|"&retstart="
operator|+
name|Integer
operator|.
name|toString
argument_list|(
name|start
argument_list|)
operator|+
literal|"&term="
decl_stmt|;
name|Pattern
name|idPattern
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"<Id>(\\d+)</Id>"
argument_list|)
decl_stmt|;
name|Pattern
name|countPattern
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"<Count>(\\d+)<\\/Count>"
argument_list|)
decl_stmt|;
name|Pattern
name|retMaxPattern
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"<RetMax>(\\d+)<\\/RetMax>"
argument_list|)
decl_stmt|;
name|Pattern
name|retStartPattern
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"<RetStart>(\\d+)<\\/RetStart>"
argument_list|)
decl_stmt|;
name|Matcher
name|idMatcher
decl_stmt|;
name|Matcher
name|countMatcher
decl_stmt|;
name|Matcher
name|retMaxMatcher
decl_stmt|;
name|Matcher
name|retStartMatcher
decl_stmt|;
name|boolean
name|doCount
init|=
literal|true
decl_stmt|;
name|SearchResult
name|result
init|=
operator|new
name|SearchResult
argument_list|()
decl_stmt|;
comment|//System.out.println(medlineUrl+term);
try|try
block|{
name|URL
name|ncbi
init|=
operator|new
name|URL
argument_list|(
name|medlineUrl
operator|+
name|term
argument_list|)
decl_stmt|;
comment|// get the ids
name|HttpURLConnection
name|ncbiCon
init|=
operator|(
name|HttpURLConnection
operator|)
name|ncbi
operator|.
name|openConnection
argument_list|()
decl_stmt|;
name|BufferedReader
name|in
init|=
operator|new
name|BufferedReader
argument_list|(
operator|new
name|InputStreamReader
argument_list|(
name|ncbi
operator|.
name|openStream
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|String
name|inLine
decl_stmt|;
while|while
condition|(
operator|(
name|inLine
operator|=
name|in
operator|.
name|readLine
argument_list|()
operator|)
operator|!=
literal|null
condition|)
block|{
comment|// get the count
name|idMatcher
operator|=
name|idPattern
operator|.
name|matcher
argument_list|(
name|inLine
argument_list|)
expr_stmt|;
if|if
condition|(
name|idMatcher
operator|.
name|find
argument_list|()
condition|)
block|{
name|result
operator|.
name|addID
argument_list|(
name|idMatcher
operator|.
name|group
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|retMaxMatcher
operator|=
name|retMaxPattern
operator|.
name|matcher
argument_list|(
name|inLine
argument_list|)
expr_stmt|;
if|if
condition|(
name|idMatcher
operator|.
name|find
argument_list|()
condition|)
block|{
name|result
operator|.
name|retmax
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|retMaxMatcher
operator|.
name|group
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|retStartMatcher
operator|=
name|retStartPattern
operator|.
name|matcher
argument_list|(
name|inLine
argument_list|)
expr_stmt|;
if|if
condition|(
name|retStartMatcher
operator|.
name|find
argument_list|()
condition|)
block|{
name|result
operator|.
name|retstart
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|retStartMatcher
operator|.
name|group
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|countMatcher
operator|=
name|countPattern
operator|.
name|matcher
argument_list|(
name|inLine
argument_list|)
expr_stmt|;
if|if
condition|(
name|doCount
operator|&&
name|countMatcher
operator|.
name|find
argument_list|()
condition|)
block|{
name|result
operator|.
name|count
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|countMatcher
operator|.
name|group
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|doCount
operator|=
literal|false
expr_stmt|;
block|}
block|}
block|}
catch|catch
parameter_list|(
name|MalformedURLException
name|e
parameter_list|)
block|{
comment|// new URL() failed
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"bad url"
argument_list|)
expr_stmt|;
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
comment|// openConnection() failed
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"connection failed"
argument_list|)
expr_stmt|;
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
return|return
name|result
return|;
block|}
DECL|method|getTitles (String[] idArrayList)
specifier|public
name|String
index|[]
name|getTitles
parameter_list|(
name|String
index|[]
name|idArrayList
parameter_list|)
block|{
name|String
index|[]
name|titles
init|=
operator|new
name|String
index|[
name|Math
operator|.
name|min
argument_list|(
name|MAX_TO_FETCH
argument_list|,
name|idArrayList
operator|.
name|length
argument_list|)
index|]
decl_stmt|;
name|String
name|temp
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|Math
operator|.
name|min
argument_list|(
name|MAX_TO_FETCH
argument_list|,
name|idArrayList
operator|.
name|length
argument_list|)
condition|;
name|i
operator|++
control|)
block|{
name|temp
operator|=
name|getOneCitation
argument_list|(
name|idArrayList
index|[
name|i
index|]
argument_list|)
expr_stmt|;
name|titles
index|[
name|i
index|]
operator|=
name|getVitalData
argument_list|(
name|temp
argument_list|)
expr_stmt|;
block|}
return|return
name|titles
return|;
block|}
comment|// get the xml for an entry
DECL|method|getOneCitation (String id)
specifier|public
name|String
name|getOneCitation
parameter_list|(
name|String
name|id
parameter_list|)
block|{
name|String
name|baseUrl
init|=
literal|"http://eutils.ncbi.nlm.nih.gov/entrez/eutils"
decl_stmt|;
name|String
name|retrieveUrl
init|=
name|baseUrl
operator|+
literal|"/efetch.fcgi?db=pubmed&retmode=xml&rettype=citation&id="
decl_stmt|;
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
try|try
block|{
name|URL
name|ncbi
init|=
operator|new
name|URL
argument_list|(
name|retrieveUrl
operator|+
name|id
argument_list|)
decl_stmt|;
name|HttpURLConnection
name|ncbiCon
init|=
operator|(
name|HttpURLConnection
operator|)
name|ncbi
operator|.
name|openConnection
argument_list|()
decl_stmt|;
name|BufferedReader
name|in
init|=
operator|new
name|BufferedReader
argument_list|(
operator|new
name|InputStreamReader
argument_list|(
name|ncbi
operator|.
name|openStream
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|String
name|inLine
decl_stmt|;
while|while
condition|(
operator|(
name|inLine
operator|=
name|in
operator|.
name|readLine
argument_list|()
operator|)
operator|!=
literal|null
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|inLine
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|MalformedURLException
name|e
parameter_list|)
block|{
comment|// new URL() failed
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"bad url"
argument_list|)
expr_stmt|;
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
comment|// openConnection() failed
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"connection failed"
argument_list|)
expr_stmt|;
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
comment|// parse out the titles from the xml
DECL|method|getVitalData (String sb)
specifier|public
name|String
name|getVitalData
parameter_list|(
name|String
name|sb
parameter_list|)
block|{
name|StringBuffer
name|result
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|Pattern
name|articleTitle
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"<ArticleTitle>(.+)</ArticleTitle>"
argument_list|)
decl_stmt|;
name|Pattern
name|authorName
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"<Author>(.+)</Author>"
argument_list|)
decl_stmt|;
name|Matcher
name|matcher
decl_stmt|;
name|matcher
operator|=
name|articleTitle
operator|.
name|matcher
argument_list|(
name|sb
argument_list|)
expr_stmt|;
if|if
condition|(
name|matcher
operator|.
name|find
argument_list|()
condition|)
name|result
operator|.
name|append
argument_list|(
literal|"Title: "
operator|+
name|matcher
operator|.
name|group
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
comment|//matcher=authorName.matcher(sb);
comment|//while (matcher.find())
comment|//   result.append("\tAuthor: "+matcher.group(1));
return|return
name|result
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
end_class

end_unit

