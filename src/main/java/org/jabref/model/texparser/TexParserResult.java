begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.model.texparser
package|package
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|texparser
package|;
end_package

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
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Objects
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
import|;
end_import

begin_import
import|import
name|org
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
name|com
operator|.
name|google
operator|.
name|common
operator|.
name|collect
operator|.
name|HashMultimap
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|common
operator|.
name|collect
operator|.
name|Multimap
import|;
end_import

begin_class
DECL|class|TexParserResult
specifier|public
class|class
name|TexParserResult
block|{
DECL|field|fileList
specifier|private
specifier|final
name|List
argument_list|<
name|Path
argument_list|>
name|fileList
decl_stmt|;
DECL|field|nestedFiles
specifier|private
specifier|final
name|List
argument_list|<
name|Path
argument_list|>
name|nestedFiles
decl_stmt|;
DECL|field|bibFiles
specifier|private
specifier|final
name|Multimap
argument_list|<
name|Path
argument_list|,
name|Path
argument_list|>
name|bibFiles
decl_stmt|;
DECL|field|citations
specifier|private
specifier|final
name|Multimap
argument_list|<
name|String
argument_list|,
name|Citation
argument_list|>
name|citations
decl_stmt|;
DECL|method|TexParserResult ()
specifier|public
name|TexParserResult
parameter_list|()
block|{
name|this
operator|.
name|fileList
operator|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
expr_stmt|;
name|this
operator|.
name|nestedFiles
operator|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
expr_stmt|;
name|this
operator|.
name|bibFiles
operator|=
name|HashMultimap
operator|.
name|create
argument_list|()
expr_stmt|;
name|this
operator|.
name|citations
operator|=
name|HashMultimap
operator|.
name|create
argument_list|()
expr_stmt|;
block|}
DECL|method|getFileList ()
specifier|public
name|List
argument_list|<
name|Path
argument_list|>
name|getFileList
parameter_list|()
block|{
return|return
name|fileList
return|;
block|}
DECL|method|getNestedFiles ()
specifier|public
name|List
argument_list|<
name|Path
argument_list|>
name|getNestedFiles
parameter_list|()
block|{
return|return
name|nestedFiles
return|;
block|}
DECL|method|getBibFiles ()
specifier|public
name|Multimap
argument_list|<
name|Path
argument_list|,
name|Path
argument_list|>
name|getBibFiles
parameter_list|()
block|{
return|return
name|bibFiles
return|;
block|}
DECL|method|getCitations ()
specifier|public
name|Multimap
argument_list|<
name|String
argument_list|,
name|Citation
argument_list|>
name|getCitations
parameter_list|()
block|{
return|return
name|citations
return|;
block|}
comment|/**      * Return a set of strings with the keys of the citations multimap.      */
DECL|method|getCitationsKeySet ()
specifier|public
name|Set
argument_list|<
name|String
argument_list|>
name|getCitationsKeySet
parameter_list|()
block|{
return|return
name|citations
operator|.
name|keySet
argument_list|()
return|;
block|}
comment|/**      * Return a collection of citations using a string as key reference.      */
DECL|method|getCitationsByKey (String key)
specifier|public
name|Collection
argument_list|<
name|Citation
argument_list|>
name|getCitationsByKey
parameter_list|(
name|String
name|key
parameter_list|)
block|{
return|return
name|citations
operator|.
name|get
argument_list|(
name|key
argument_list|)
return|;
block|}
comment|/**      * Return a collection of citations using a BibEntry as reference.      */
DECL|method|getCitationsByKey (BibEntry entry)
specifier|public
name|Collection
argument_list|<
name|Citation
argument_list|>
name|getCitationsByKey
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
return|return
name|entry
operator|.
name|getCiteKeyOptional
argument_list|()
operator|.
name|map
argument_list|(
name|this
operator|::
name|getCitationsByKey
argument_list|)
operator|.
name|orElse
argument_list|(
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|)
return|;
block|}
comment|/**      * Add a list of files to fileList or nestedFiles, depending on whether this is the first list.      */
DECL|method|addFiles (List<Path> texFiles)
specifier|public
name|void
name|addFiles
parameter_list|(
name|List
argument_list|<
name|Path
argument_list|>
name|texFiles
parameter_list|)
block|{
if|if
condition|(
name|fileList
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|fileList
operator|.
name|addAll
argument_list|(
name|texFiles
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|nestedFiles
operator|.
name|addAll
argument_list|(
name|texFiles
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Add a bibliography file to the BIB files set.      */
DECL|method|addBibFile (Path file, Path bibFile)
specifier|public
name|void
name|addBibFile
parameter_list|(
name|Path
name|file
parameter_list|,
name|Path
name|bibFile
parameter_list|)
block|{
name|bibFiles
operator|.
name|put
argument_list|(
name|file
argument_list|,
name|bibFile
argument_list|)
expr_stmt|;
block|}
comment|/**      * Add a citation to the citations multimap.      */
DECL|method|addKey (String key, Path file, int lineNumber, int start, int end, String line)
specifier|public
name|void
name|addKey
parameter_list|(
name|String
name|key
parameter_list|,
name|Path
name|file
parameter_list|,
name|int
name|lineNumber
parameter_list|,
name|int
name|start
parameter_list|,
name|int
name|end
parameter_list|,
name|String
name|line
parameter_list|)
block|{
name|Citation
name|citation
init|=
operator|new
name|Citation
argument_list|(
name|file
argument_list|,
name|lineNumber
argument_list|,
name|start
argument_list|,
name|end
argument_list|,
name|line
argument_list|)
decl_stmt|;
name|citations
operator|.
name|put
argument_list|(
name|key
argument_list|,
name|citation
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|String
operator|.
name|format
argument_list|(
literal|"TexParserResult{fileList=%s, nestedFiles=%s, bibFiles=%s, citations=%s}"
argument_list|,
name|this
operator|.
name|fileList
argument_list|,
name|this
operator|.
name|nestedFiles
argument_list|,
name|this
operator|.
name|bibFiles
argument_list|,
name|this
operator|.
name|citations
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|equals (Object obj)
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|obj
parameter_list|)
block|{
if|if
condition|(
name|this
operator|==
name|obj
condition|)
block|{
return|return
literal|true
return|;
block|}
if|if
condition|(
name|obj
operator|==
literal|null
operator|||
name|getClass
argument_list|()
operator|!=
name|obj
operator|.
name|getClass
argument_list|()
condition|)
block|{
return|return
literal|false
return|;
block|}
name|TexParserResult
name|that
init|=
operator|(
name|TexParserResult
operator|)
name|obj
decl_stmt|;
return|return
name|Objects
operator|.
name|equals
argument_list|(
name|fileList
argument_list|,
name|that
operator|.
name|fileList
argument_list|)
operator|&&
name|Objects
operator|.
name|equals
argument_list|(
name|nestedFiles
argument_list|,
name|that
operator|.
name|nestedFiles
argument_list|)
operator|&&
name|Objects
operator|.
name|equals
argument_list|(
name|bibFiles
argument_list|,
name|that
operator|.
name|bibFiles
argument_list|)
operator|&&
name|Objects
operator|.
name|equals
argument_list|(
name|citations
argument_list|,
name|that
operator|.
name|citations
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|hashCode ()
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|Objects
operator|.
name|hash
argument_list|(
name|fileList
argument_list|,
name|nestedFiles
argument_list|,
name|bibFiles
argument_list|,
name|citations
argument_list|)
return|;
block|}
block|}
end_class

end_unit

