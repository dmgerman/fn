begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.auxparser
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|auxparser
package|;
end_package

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|database
operator|.
name|BibDatabase
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
name|model
operator|.
name|entry
operator|.
name|BibEntry
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
name|model
operator|.
name|entry
operator|.
name|IdGenerator
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|Log
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
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
name|java
operator|.
name|nio
operator|.
name|file
operator|.
name|Path
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|*
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
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Pattern
import|;
end_import

begin_comment
comment|/**  * LaTeX Aux to BibTeX Parser  *<p>  * Extracts a subset of BibTeX entries from a BibDatabase that are included in an aux file.  */
end_comment

begin_class
DECL|class|AuxParser
specifier|public
class|class
name|AuxParser
block|{
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Log
name|LOGGER
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|AuxParser
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|CITE_PATTERN
specifier|private
specifier|static
specifier|final
name|Pattern
name|CITE_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"\\\\(citation|abx@aux@cite)\\{(.+)\\}"
argument_list|)
decl_stmt|;
DECL|field|INPUT_PATTERN
specifier|private
specifier|static
specifier|final
name|Pattern
name|INPUT_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"\\\\@input\\{(.+)\\}"
argument_list|)
decl_stmt|;
DECL|field|auxFile
specifier|private
specifier|final
name|String
name|auxFile
decl_stmt|;
DECL|field|masterDatabase
specifier|private
specifier|final
name|BibDatabase
name|masterDatabase
decl_stmt|;
comment|/**      * Generates a database based on the given aux file and BibTeX database      *      * @param auxFile  Path to the LaTeX aux file      * @param database BibTeX database      */
DECL|method|AuxParser (String auxFile, BibDatabase database)
specifier|public
name|AuxParser
parameter_list|(
name|String
name|auxFile
parameter_list|,
name|BibDatabase
name|database
parameter_list|)
block|{
name|this
operator|.
name|auxFile
operator|=
name|auxFile
expr_stmt|;
name|masterDatabase
operator|=
name|database
expr_stmt|;
block|}
comment|/**      * Executes the parsing logic and returns a result containing all information and the generated BibDatabase.      *      * @return an AuxParserResult containing the generated BibDatabase and parsing statistics      */
DECL|method|parse ()
specifier|public
name|AuxParserResult
name|parse
parameter_list|()
block|{
return|return
name|parseAuxFile
argument_list|()
return|;
block|}
comment|/*      * Parses the aux file and extracts all bib keys.      * Also supports nested aux files (latex \\include).      *      * There exists no specification of the aux file.      * Every package, class or document can write to the aux file.      * The aux file consists of LaTeX macros and is read at the \begin{document} and again at the \end{document}.      *      * BibTeX citation: \citation{x,y,z}      * Biblatex citation: \abx@aux@cite{x,y,z}      * Nested aux files: \@input{x}      */
DECL|method|parseAuxFile ()
specifier|private
name|AuxParserResult
name|parseAuxFile
parameter_list|()
block|{
name|AuxParserResult
name|result
init|=
operator|new
name|AuxParserResult
argument_list|(
name|masterDatabase
argument_list|)
decl_stmt|;
comment|// nested aux files
name|List
argument_list|<
name|String
argument_list|>
name|fileList
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|(
literal|1
argument_list|)
decl_stmt|;
name|fileList
operator|.
name|add
argument_list|(
name|auxFile
argument_list|)
expr_stmt|;
name|int
name|fileIndex
init|=
literal|0
decl_stmt|;
while|while
condition|(
name|fileIndex
operator|<
name|fileList
operator|.
name|size
argument_list|()
condition|)
block|{
name|String
name|file
init|=
name|fileList
operator|.
name|get
argument_list|(
name|fileIndex
argument_list|)
decl_stmt|;
try|try
init|(
name|BufferedReader
name|br
init|=
operator|new
name|BufferedReader
argument_list|(
operator|new
name|FileReader
argument_list|(
name|file
argument_list|)
argument_list|)
init|)
block|{
name|String
name|line
decl_stmt|;
while|while
condition|(
operator|(
name|line
operator|=
name|br
operator|.
name|readLine
argument_list|()
operator|)
operator|!=
literal|null
condition|)
block|{
name|Matcher
name|citeMatch
init|=
name|CITE_PATTERN
operator|.
name|matcher
argument_list|(
name|line
argument_list|)
decl_stmt|;
while|while
condition|(
name|citeMatch
operator|.
name|find
argument_list|()
condition|)
block|{
name|String
name|keyString
init|=
name|citeMatch
operator|.
name|group
argument_list|(
literal|2
argument_list|)
decl_stmt|;
name|String
index|[]
name|keys
init|=
name|keyString
operator|.
name|split
argument_list|(
literal|","
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|key
range|:
name|keys
control|)
block|{
name|result
operator|.
name|getUniqueKeys
argument_list|()
operator|.
name|add
argument_list|(
name|key
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
name|Matcher
name|inputMatch
init|=
name|INPUT_PATTERN
operator|.
name|matcher
argument_list|(
name|line
argument_list|)
decl_stmt|;
while|while
condition|(
name|inputMatch
operator|.
name|find
argument_list|()
condition|)
block|{
name|String
name|inputString
init|=
name|inputMatch
operator|.
name|group
argument_list|(
literal|1
argument_list|)
decl_stmt|;
name|String
name|inputFile
init|=
name|inputString
decl_stmt|;
name|Path
name|rootPath
init|=
operator|new
name|File
argument_list|(
name|auxFile
argument_list|)
operator|.
name|toPath
argument_list|()
operator|.
name|getParent
argument_list|()
decl_stmt|;
if|if
condition|(
name|rootPath
operator|!=
literal|null
condition|)
block|{
name|inputFile
operator|=
name|rootPath
operator|.
name|resolve
argument_list|(
name|inputString
argument_list|)
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|fileList
operator|.
name|contains
argument_list|(
name|inputFile
argument_list|)
condition|)
block|{
name|fileList
operator|.
name|add
argument_list|(
name|inputFile
argument_list|)
expr_stmt|;
name|result
operator|.
name|increaseNestedAuxFilesCounter
argument_list|()
expr_stmt|;
block|}
block|}
block|}
block|}
catch|catch
parameter_list|(
name|FileNotFoundException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Cannot locate input file"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Problem opening file"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
name|fileIndex
operator|++
expr_stmt|;
block|}
name|resolveTags
argument_list|(
name|result
argument_list|)
expr_stmt|;
return|return
name|result
return|;
block|}
comment|/*      * Try to find an equivalent BibTeX entry inside the reference database for all keys inside the aux file.      */
DECL|method|resolveTags (AuxParserResult result)
specifier|private
name|void
name|resolveTags
parameter_list|(
name|AuxParserResult
name|result
parameter_list|)
block|{
for|for
control|(
name|String
name|key
range|:
name|result
operator|.
name|getUniqueKeys
argument_list|()
control|)
block|{
name|BibEntry
name|entry
init|=
name|masterDatabase
operator|.
name|getEntryByKey
argument_list|(
name|key
argument_list|)
decl_stmt|;
if|if
condition|(
name|entry
operator|==
literal|null
condition|)
block|{
name|result
operator|.
name|getUnresolvedKeys
argument_list|()
operator|.
name|add
argument_list|(
name|key
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|insertEntry
argument_list|(
name|entry
argument_list|,
name|result
argument_list|)
expr_stmt|;
name|resolveCrossReferences
argument_list|(
name|entry
argument_list|,
name|result
argument_list|)
expr_stmt|;
block|}
block|}
comment|// Copy database definitions
if|if
condition|(
name|result
operator|.
name|getGeneratedBibDatabase
argument_list|()
operator|.
name|getEntryCount
argument_list|()
operator|>
literal|0
condition|)
block|{
name|result
operator|.
name|getGeneratedBibDatabase
argument_list|()
operator|.
name|copyPreamble
argument_list|(
name|masterDatabase
argument_list|)
expr_stmt|;
name|result
operator|.
name|getGeneratedBibDatabase
argument_list|()
operator|.
name|copyStrings
argument_list|(
name|masterDatabase
argument_list|)
expr_stmt|;
block|}
block|}
comment|/*      * Resolves and adds CrossRef entries      */
DECL|method|resolveCrossReferences (BibEntry entry, AuxParserResult result)
specifier|private
name|void
name|resolveCrossReferences
parameter_list|(
name|BibEntry
name|entry
parameter_list|,
name|AuxParserResult
name|result
parameter_list|)
block|{
name|entry
operator|.
name|getFieldOptional
argument_list|(
literal|"crossref"
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|crossref
lambda|->
block|{
if|if
condition|(
operator|!
name|result
operator|.
name|getUniqueKeys
argument_list|()
operator|.
name|contains
argument_list|(
name|crossref
argument_list|)
condition|)
block|{
name|BibEntry
name|refEntry
init|=
name|masterDatabase
operator|.
name|getEntryByKey
argument_list|(
name|crossref
argument_list|)
decl_stmt|;
if|if
condition|(
name|refEntry
operator|==
literal|null
condition|)
block|{
name|result
operator|.
name|getUnresolvedKeys
argument_list|()
operator|.
name|add
argument_list|(
name|crossref
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|insertEntry
argument_list|(
name|refEntry
argument_list|,
name|result
argument_list|)
expr_stmt|;
name|result
operator|.
name|increaseCrossRefEntriesCounter
argument_list|()
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
block|}
comment|/*      * Insert a clone of the given entry. The clone is given a new unique ID.      */
DECL|method|insertEntry (BibEntry entry, AuxParserResult result)
specifier|private
name|void
name|insertEntry
parameter_list|(
name|BibEntry
name|entry
parameter_list|,
name|AuxParserResult
name|result
parameter_list|)
block|{
name|BibEntry
name|clonedEntry
init|=
operator|(
name|BibEntry
operator|)
name|entry
operator|.
name|clone
argument_list|()
decl_stmt|;
name|clonedEntry
operator|.
name|setId
argument_list|(
name|IdGenerator
operator|.
name|next
argument_list|()
argument_list|)
expr_stmt|;
name|result
operator|.
name|getGeneratedBibDatabase
argument_list|()
operator|.
name|insertEntry
argument_list|(
name|clonedEntry
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit
