begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.maintable
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|maintable
package|;
end_package

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
name|Optional
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|stream
operator|.
name|Collectors
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|beans
operator|.
name|binding
operator|.
name|ObjectBinding
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|beans
operator|.
name|property
operator|.
name|SimpleObjectProperty
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|beans
operator|.
name|value
operator|.
name|ObservableValue
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|specialfields
operator|.
name|SpecialFieldValueViewModel
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
name|database
operator|.
name|BibDatabaseContext
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
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|FileFieldParser
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
name|LinkedFile
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
name|field
operator|.
name|Field
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
name|field
operator|.
name|SpecialField
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
name|field
operator|.
name|StandardField
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
name|groups
operator|.
name|AbstractGroup
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
name|groups
operator|.
name|GroupTreeNode
import|;
end_import

begin_import
import|import
name|org
operator|.
name|fxmisc
operator|.
name|easybind
operator|.
name|EasyBind
import|;
end_import

begin_class
DECL|class|BibEntryTableViewModel
specifier|public
class|class
name|BibEntryTableViewModel
block|{
DECL|field|entry
specifier|private
specifier|final
name|BibEntry
name|entry
decl_stmt|;
DECL|method|BibEntryTableViewModel (BibEntry entry)
specifier|public
name|BibEntryTableViewModel
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
name|this
operator|.
name|entry
operator|=
name|entry
expr_stmt|;
block|}
DECL|method|getEntry ()
specifier|public
name|BibEntry
name|getEntry
parameter_list|()
block|{
return|return
name|entry
return|;
block|}
DECL|method|getResolvedFieldOrAlias (Field field, BibDatabase database)
specifier|public
name|Optional
argument_list|<
name|String
argument_list|>
name|getResolvedFieldOrAlias
parameter_list|(
name|Field
name|field
parameter_list|,
name|BibDatabase
name|database
parameter_list|)
block|{
return|return
name|entry
operator|.
name|getResolvedFieldOrAlias
argument_list|(
name|field
argument_list|,
name|database
argument_list|)
return|;
block|}
DECL|method|getField (Field field)
specifier|public
name|ObjectBinding
argument_list|<
name|String
argument_list|>
name|getField
parameter_list|(
name|Field
name|field
parameter_list|)
block|{
return|return
name|entry
operator|.
name|getFieldBinding
argument_list|(
name|field
argument_list|)
return|;
block|}
DECL|method|getSpecialField (SpecialField field)
specifier|public
name|ObservableValue
argument_list|<
name|Optional
argument_list|<
name|SpecialFieldValueViewModel
argument_list|>
argument_list|>
name|getSpecialField
parameter_list|(
name|SpecialField
name|field
parameter_list|)
block|{
return|return
name|EasyBind
operator|.
name|map
argument_list|(
name|getField
argument_list|(
name|field
argument_list|)
argument_list|,
name|value
lambda|->
name|field
operator|.
name|parseValue
argument_list|(
name|value
argument_list|)
operator|.
name|map
argument_list|(
name|SpecialFieldValueViewModel
operator|::
operator|new
argument_list|)
argument_list|)
return|;
block|}
DECL|method|getLinkedFiles ()
specifier|public
name|ObservableValue
argument_list|<
name|List
argument_list|<
name|LinkedFile
argument_list|>
argument_list|>
name|getLinkedFiles
parameter_list|()
block|{
return|return
name|EasyBind
operator|.
name|map
argument_list|(
name|getField
argument_list|(
name|StandardField
operator|.
name|FILE
argument_list|)
argument_list|,
name|FileFieldParser
operator|::
name|parse
argument_list|)
return|;
block|}
DECL|method|getMatchedGroups (BibDatabaseContext database)
specifier|public
name|ObservableValue
argument_list|<
name|List
argument_list|<
name|AbstractGroup
argument_list|>
argument_list|>
name|getMatchedGroups
parameter_list|(
name|BibDatabaseContext
name|database
parameter_list|)
block|{
name|SimpleObjectProperty
argument_list|<
name|List
argument_list|<
name|AbstractGroup
argument_list|>
argument_list|>
name|matchedGroups
init|=
operator|new
name|SimpleObjectProperty
argument_list|<>
argument_list|(
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|)
decl_stmt|;
name|Optional
argument_list|<
name|GroupTreeNode
argument_list|>
name|root
init|=
name|database
operator|.
name|getMetaData
argument_list|()
operator|.
name|getGroups
argument_list|()
decl_stmt|;
if|if
condition|(
name|root
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|List
argument_list|<
name|AbstractGroup
argument_list|>
name|groups
init|=
name|root
operator|.
name|get
argument_list|()
operator|.
name|getMatchingGroups
argument_list|(
name|entry
argument_list|)
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|GroupTreeNode
operator|::
name|getGroup
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
decl_stmt|;
name|groups
operator|.
name|remove
argument_list|(
name|root
operator|.
name|get
argument_list|()
operator|.
name|getGroup
argument_list|()
argument_list|)
expr_stmt|;
name|matchedGroups
operator|.
name|setValue
argument_list|(
name|groups
argument_list|)
expr_stmt|;
block|}
return|return
name|matchedGroups
return|;
block|}
block|}
end_class

end_unit

