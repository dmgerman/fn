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
DECL|field|MAX_TO_FETCH
specifier|final
name|int
name|MAX_TO_FETCH
init|=
literal|10
decl_stmt|;
DECL|field|header
name|SidePaneHeader
name|header
init|=
operator|new
name|SidePaneHeader
argument_list|(
literal|"Fetch Medline"
argument_list|,
name|GUIGlobals
operator|.
name|fetchMedlineIcon
argument_list|,
name|this
argument_list|)
decl_stmt|;
DECL|field|panel
name|BasePanel
name|panel
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
DECL|method|MedlineFetcher (BasePanel panel_, SidePaneManager p0)
specifier|public
name|MedlineFetcher
parameter_list|(
name|BasePanel
name|panel_
parameter_list|,
name|SidePaneManager
name|p0
parameter_list|)
block|{
name|super
argument_list|(
name|p0
argument_list|)
expr_stmt|;
name|panel
operator|=
name|panel_
expr_stmt|;
name|help
operator|=
operator|new
name|HelpAction
argument_list|(
name|panel
operator|.
name|frame
argument_list|()
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
name|tf
operator|.
name|setMinimumSize
argument_list|(
operator|new
name|Dimension
argument_list|(
literal|1
argument_list|,
literal|1
argument_list|)
argument_list|)
expr_stmt|;
comment|//add(hd, BorderLayout.NORTH);
comment|//ok.setToolTipText(Globals.lang("Fetch Medline"));
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
name|con
operator|.
name|insets
operator|=
operator|new
name|Insets
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|,
literal|2
argument_list|,
literal|0
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
literal|0
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|header
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|header
argument_list|)
expr_stmt|;
name|con
operator|.
name|insets
operator|=
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
expr_stmt|;
comment|//    pan.setLayout(gbl);
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
name|add
argument_list|(
name|tf
argument_list|)
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
name|add
argument_list|(
name|go
argument_list|)
expr_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|0
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
DECL|method|fetchById ()
specifier|public
name|void
name|fetchById
parameter_list|()
block|{
name|String
name|idList
init|=
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
decl_stmt|;
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
if|if
condition|(
name|panel
operator|.
name|prefs
argument_list|()
operator|.
name|getBoolean
argument_list|(
literal|"useOwner"
argument_list|)
condition|)
block|{
name|Util
operator|.
name|setDefaultOwner
argument_list|(
name|bibs
argument_list|,
name|panel
operator|.
name|prefs
argument_list|()
operator|.
name|get
argument_list|(
literal|"defaultOwner"
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|tf
operator|.
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|NamedCompound
name|ce
init|=
operator|new
name|NamedCompound
argument_list|(
literal|"fetch Medline"
argument_list|)
decl_stmt|;
name|Iterator
name|i
init|=
name|bibs
operator|.
name|iterator
argument_list|()
decl_stmt|;
while|while
condition|(
name|i
operator|.
name|hasNext
argument_list|()
condition|)
block|{
try|try
block|{
name|BibtexEntry
name|be
init|=
operator|(
name|BibtexEntry
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
name|id
init|=
name|Util
operator|.
name|createId
argument_list|(
name|be
operator|.
name|getType
argument_list|()
argument_list|,
name|panel
operator|.
name|database
argument_list|()
argument_list|)
decl_stmt|;
name|be
operator|.
name|setId
argument_list|(
name|id
argument_list|)
expr_stmt|;
name|panel
operator|.
name|database
argument_list|()
operator|.
name|insertEntry
argument_list|(
name|be
argument_list|)
expr_stmt|;
name|ce
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableInsertEntry
argument_list|(
name|panel
operator|.
name|database
argument_list|()
argument_list|,
name|be
argument_list|,
name|panel
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|KeyCollisionException
name|ex
parameter_list|)
block|{           }
block|}
name|ce
operator|.
name|end
argument_list|()
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
name|panel
operator|.
name|undoManager
operator|.
name|addEdit
argument_list|(
name|ce
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
name|String
name|idList
init|=
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
decl_stmt|;
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
name|fetchById
argument_list|()
expr_stmt|;
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
comment|// Fetch by author.
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
literal|"Fetching Medline by author..."
argument_list|)
argument_list|)
expr_stmt|;
comment|// my stuff
comment|//---------------------------
name|idList
operator|=
name|setupTerm
argument_list|(
name|idList
argument_list|)
expr_stmt|;
comment|// fix the syntax
name|String
index|[]
name|idListArray
init|=
name|getIds
argument_list|(
name|idList
argument_list|)
decl_stmt|;
comment|// get the ids from entrez
comment|//int idMax=idListArray.length; // check length
comment|//Util.pr("fikk "+idListArray.length);
comment|//String[] titles=new String[idMax]; // prepare an array of titles for the dialog
comment|//titles=getTitles(idListArray);
comment|//Util.pr("titler "+titles.length);
comment|// get a list of which ids the user wants.
if|if
condition|(
name|idListArray
operator|.
name|length
operator|>
literal|0
condition|)
block|{
comment|// prompt the user to select articles
name|authorDialog
operator|=
operator|new
name|AuthorDialog
argument_list|(
name|jFrame
argument_list|,
name|this
argument_list|,
name|idListArray
argument_list|)
expr_stmt|;
comment|//boolean[] picks = authorDialog.showDialog();
block|}
comment|//idList="";
comment|//for (int i=0; i<titles.length;i++){
comment|//if (picks[i]){
comment|//  idList+=idListArray[i]+",";
comment|//System.out.println(idListArray[i]);
comment|//}
comment|//}
comment|//System.out.println(idList);
comment|//----------------------------
comment|// end my stuff
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
name|tf
operator|.
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|NamedCompound
name|ce
init|=
operator|new
name|NamedCompound
argument_list|(
literal|"fetch Medline"
argument_list|)
decl_stmt|;
name|Iterator
name|i
init|=
name|bibs
operator|.
name|iterator
argument_list|()
decl_stmt|;
while|while
condition|(
name|i
operator|.
name|hasNext
argument_list|()
condition|)
block|{
try|try
block|{
name|BibtexEntry
name|be
init|=
operator|(
name|BibtexEntry
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
name|id
init|=
name|Util
operator|.
name|createId
argument_list|(
name|be
operator|.
name|getType
argument_list|()
argument_list|,
name|panel
operator|.
name|database
argument_list|()
argument_list|)
decl_stmt|;
name|be
operator|.
name|setId
argument_list|(
name|id
argument_list|)
expr_stmt|;
name|panel
operator|.
name|database
argument_list|()
operator|.
name|insertEntry
argument_list|(
name|be
argument_list|)
expr_stmt|;
name|ce
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableInsertEntry
argument_list|(
name|panel
operator|.
name|database
argument_list|()
argument_list|,
name|be
argument_list|,
name|panel
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|KeyCollisionException
name|ex
parameter_list|)
block|{ 		    }
block|}
name|ce
operator|.
name|end
argument_list|()
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
name|panel
operator|.
name|undoManager
operator|.
name|addEdit
argument_list|(
name|ce
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
comment|//JOptionPane.showMessageDialog(panel.frame(), Globals.lang("Please enter a semicolon or comma separated list of either Medline IDs (numbers), "+
comment|//    "or author names to search for."),Globals.lang("Input error"),JOptionPane.ERROR_MESSAGE);
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
DECL|method|getIds (String term)
specifier|public
name|String
index|[]
name|getIds
parameter_list|(
name|String
name|term
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
literal|"/esearch.fcgi?retmax=20&usehistory=y&db=pubmed&term="
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
name|Matcher
name|matcher
decl_stmt|;
name|boolean
name|doCount
init|=
literal|true
decl_stmt|;
name|int
name|count
init|=
literal|0
decl_stmt|;
name|String
index|[]
name|id
init|=
operator|new
name|String
index|[
literal|10
index|]
decl_stmt|;
comment|// doesn't matter
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
if|if
condition|(
name|doCount
condition|)
block|{
comment|// get the count
name|matcher
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
name|matcher
operator|.
name|find
argument_list|()
condition|)
block|{
name|count
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|matcher
operator|.
name|group
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|id
operator|=
operator|new
name|String
index|[
name|count
index|]
expr_stmt|;
name|count
operator|=
literal|0
expr_stmt|;
name|doCount
operator|=
literal|false
expr_stmt|;
block|}
block|}
else|else
block|{
comment|// get the ids
name|matcher
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
name|matcher
operator|.
name|find
argument_list|()
condition|)
block|{
name|id
index|[
name|count
operator|++
index|]
operator|=
name|matcher
operator|.
name|group
argument_list|(
literal|1
argument_list|)
expr_stmt|;
block|}
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
name|id
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

