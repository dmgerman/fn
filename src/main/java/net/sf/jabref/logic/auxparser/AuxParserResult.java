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
name|HashSet
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
name|Set
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
name|logic
operator|.
name|l10n
operator|.
name|Localization
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
name|BibtexString
import|;
end_import

begin_class
DECL|class|AuxParserResult
specifier|public
class|class
name|AuxParserResult
block|{
DECL|field|masterDatabase
specifier|private
specifier|final
name|BibDatabase
name|masterDatabase
decl_stmt|;
DECL|field|uniqueKeys
specifier|private
specifier|final
name|Set
argument_list|<
name|String
argument_list|>
name|uniqueKeys
init|=
operator|new
name|HashSet
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|unresolvedKeys
specifier|private
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|unresolvedKeys
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|auxDatabase
specifier|private
specifier|final
name|BibDatabase
name|auxDatabase
init|=
operator|new
name|BibDatabase
argument_list|()
decl_stmt|;
DECL|field|nestedAuxCount
specifier|private
name|int
name|nestedAuxCount
decl_stmt|;
DECL|field|crossRefEntriesCount
specifier|private
name|int
name|crossRefEntriesCount
decl_stmt|;
DECL|field|insertedStrings
specifier|private
name|int
name|insertedStrings
decl_stmt|;
DECL|method|AuxParserResult (BibDatabase masterDatabase)
specifier|public
name|AuxParserResult
parameter_list|(
name|BibDatabase
name|masterDatabase
parameter_list|)
block|{
name|this
operator|.
name|masterDatabase
operator|=
name|masterDatabase
expr_stmt|;
block|}
DECL|method|getGeneratedBibDatabase ()
specifier|public
name|BibDatabase
name|getGeneratedBibDatabase
parameter_list|()
block|{
return|return
name|auxDatabase
return|;
block|}
DECL|method|getUnresolvedKeys ()
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getUnresolvedKeys
parameter_list|()
block|{
return|return
name|unresolvedKeys
return|;
block|}
DECL|method|getFoundKeysInAux ()
specifier|public
name|int
name|getFoundKeysInAux
parameter_list|()
block|{
return|return
name|uniqueKeys
operator|.
name|size
argument_list|()
return|;
block|}
DECL|method|getResolvedKeysCount ()
specifier|public
name|int
name|getResolvedKeysCount
parameter_list|()
block|{
return|return
name|auxDatabase
operator|.
name|getEntryCount
argument_list|()
operator|-
name|crossRefEntriesCount
return|;
block|}
DECL|method|getUnresolvedKeysCount ()
specifier|public
name|int
name|getUnresolvedKeysCount
parameter_list|()
block|{
return|return
name|unresolvedKeys
operator|.
name|size
argument_list|()
return|;
block|}
comment|/**      * Query the number of extra entries pulled in due to crossrefs from other entries.      *      * @return The number of additional entries pulled in due to crossref      */
DECL|method|getCrossRefEntriesCount ()
specifier|public
name|int
name|getCrossRefEntriesCount
parameter_list|()
block|{
return|return
name|crossRefEntriesCount
return|;
block|}
DECL|method|increaseCrossRefEntriesCounter ()
specifier|public
name|void
name|increaseCrossRefEntriesCounter
parameter_list|()
block|{
name|crossRefEntriesCount
operator|++
expr_stmt|;
block|}
DECL|method|increaseNestedAuxFilesCounter ()
specifier|public
name|void
name|increaseNestedAuxFilesCounter
parameter_list|()
block|{
name|nestedAuxCount
operator|++
expr_stmt|;
block|}
DECL|method|insertStrings (Collection<BibtexString> usedStrings)
specifier|public
name|void
name|insertStrings
parameter_list|(
name|Collection
argument_list|<
name|BibtexString
argument_list|>
name|usedStrings
parameter_list|)
block|{
for|for
control|(
name|BibtexString
name|string
range|:
name|usedStrings
control|)
block|{
name|auxDatabase
operator|.
name|addString
argument_list|(
name|string
argument_list|)
expr_stmt|;
name|insertedStrings
operator|++
expr_stmt|;
block|}
block|}
comment|/**      * Prints parsing statistics      *      * @param includeMissingEntries      * @return      */
DECL|method|getInformation (boolean includeMissingEntries)
specifier|public
name|String
name|getInformation
parameter_list|(
name|boolean
name|includeMissingEntries
parameter_list|)
block|{
name|StringBuilder
name|result
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|result
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"keys in library"
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|' '
argument_list|)
operator|.
name|append
argument_list|(
name|masterDatabase
operator|.
name|getEntryCount
argument_list|()
argument_list|)
operator|.
name|append
argument_list|(
literal|'\n'
argument_list|)
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"found in AUX file"
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|' '
argument_list|)
operator|.
name|append
argument_list|(
name|getFoundKeysInAux
argument_list|()
argument_list|)
operator|.
name|append
argument_list|(
literal|'\n'
argument_list|)
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"resolved"
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|' '
argument_list|)
operator|.
name|append
argument_list|(
name|getResolvedKeysCount
argument_list|()
argument_list|)
operator|.
name|append
argument_list|(
literal|'\n'
argument_list|)
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"not found"
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|' '
argument_list|)
operator|.
name|append
argument_list|(
name|getUnresolvedKeysCount
argument_list|()
argument_list|)
operator|.
name|append
argument_list|(
literal|'\n'
argument_list|)
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"crossreferenced entries included"
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|' '
argument_list|)
operator|.
name|append
argument_list|(
name|crossRefEntriesCount
argument_list|)
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"strings included"
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|' '
argument_list|)
operator|.
name|append
argument_list|(
name|insertedStrings
argument_list|)
operator|.
name|append
argument_list|(
literal|'\n'
argument_list|)
expr_stmt|;
if|if
condition|(
name|includeMissingEntries
operator|&&
operator|(
name|getUnresolvedKeysCount
argument_list|()
operator|>
literal|0
operator|)
condition|)
block|{
for|for
control|(
name|String
name|entry
range|:
name|unresolvedKeys
control|)
block|{
name|result
operator|.
name|append
argument_list|(
name|entry
argument_list|)
operator|.
name|append
argument_list|(
literal|'\n'
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|nestedAuxCount
operator|>
literal|0
condition|)
block|{
name|result
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"nested_AUX_files"
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|' '
argument_list|)
operator|.
name|append
argument_list|(
name|nestedAuxCount
argument_list|)
expr_stmt|;
block|}
return|return
name|result
operator|.
name|toString
argument_list|()
return|;
block|}
DECL|method|getUniqueKeys ()
specifier|public
name|Set
argument_list|<
name|String
argument_list|>
name|getUniqueKeys
parameter_list|()
block|{
return|return
name|uniqueKeys
return|;
block|}
block|}
end_class

end_unit

