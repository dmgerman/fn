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
name|HashMap
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
name|Map
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
name|database
operator|.
name|BibDatabase
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
name|BibtexString
import|;
end_import

begin_class
DECL|class|TexParserResult
specifier|public
class|class
name|TexParserResult
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
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|Citation
argument_list|>
argument_list|>
name|uniqueKeys
init|=
operator|new
name|HashMap
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
DECL|field|texDatabase
specifier|private
specifier|final
name|BibDatabase
name|texDatabase
init|=
operator|new
name|BibDatabase
argument_list|()
decl_stmt|;
DECL|field|insertedStrings
specifier|private
name|int
name|insertedStrings
init|=
literal|0
decl_stmt|;
DECL|field|nestedFilesCount
specifier|private
name|int
name|nestedFilesCount
init|=
literal|0
decl_stmt|;
DECL|field|crossRefEntriesCount
specifier|private
name|int
name|crossRefEntriesCount
init|=
literal|0
decl_stmt|;
DECL|method|TexParserResult (BibDatabase masterDatabase)
specifier|public
name|TexParserResult
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
DECL|method|getMasterDatabase ()
specifier|public
name|BibDatabase
name|getMasterDatabase
parameter_list|()
block|{
return|return
name|masterDatabase
return|;
block|}
DECL|method|getUniqueKeys ()
specifier|public
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|Citation
argument_list|>
argument_list|>
name|getUniqueKeys
parameter_list|()
block|{
return|return
name|uniqueKeys
return|;
block|}
DECL|method|getFoundKeysInTex ()
specifier|public
name|int
name|getFoundKeysInTex
parameter_list|()
block|{
return|return
name|uniqueKeys
operator|.
name|size
argument_list|()
return|;
block|}
DECL|method|getCitationsCountByKey (String key)
specifier|public
name|int
name|getCitationsCountByKey
parameter_list|(
name|String
name|key
parameter_list|)
block|{
return|return
name|uniqueKeys
operator|.
name|get
argument_list|(
name|key
argument_list|)
operator|.
name|size
argument_list|()
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
DECL|method|getGeneratedBibDatabase ()
specifier|public
name|BibDatabase
name|getGeneratedBibDatabase
parameter_list|()
block|{
return|return
name|texDatabase
return|;
block|}
DECL|method|getResolvedKeysCount ()
specifier|public
name|int
name|getResolvedKeysCount
parameter_list|()
block|{
return|return
name|texDatabase
operator|.
name|getEntryCount
argument_list|()
operator|-
name|crossRefEntriesCount
return|;
block|}
DECL|method|getInsertedStrings ()
specifier|public
name|int
name|getInsertedStrings
parameter_list|()
block|{
return|return
name|insertedStrings
return|;
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
name|texDatabase
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
DECL|method|getNestedFilesCount ()
specifier|public
name|int
name|getNestedFilesCount
parameter_list|()
block|{
return|return
name|nestedFilesCount
return|;
block|}
DECL|method|increaseNestedFilesCounter ()
specifier|public
name|void
name|increaseNestedFilesCounter
parameter_list|()
block|{
name|nestedFilesCount
operator|++
expr_stmt|;
block|}
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
block|}
end_class

end_unit
