begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.model.metadata
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|metadata
package|;
end_package

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|charset
operator|.
name|Charset
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
name|Optional
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
name|bibtexkeypattern
operator|.
name|AbstractBibtexKeyPattern
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
name|bibtexkeypattern
operator|.
name|DatabaseBibtexKeyPattern
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
name|bibtexkeypattern
operator|.
name|GlobalBibtexKeyPattern
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
name|cleanup
operator|.
name|FieldFormatterCleanups
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
name|BibDatabaseMode
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
name|event
operator|.
name|ChangePropagation
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
name|FieldName
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
name|groups
operator|.
name|GroupTreeNode
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
name|groups
operator|.
name|event
operator|.
name|GroupUpdatedEvent
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
name|metadata
operator|.
name|event
operator|.
name|MetaDataChangedEvent
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
name|eventbus
operator|.
name|EventBus
import|;
end_import

begin_class
DECL|class|MetaData
specifier|public
class|class
name|MetaData
block|{
DECL|field|META_FLAG
specifier|public
specifier|static
specifier|final
name|String
name|META_FLAG
init|=
literal|"jabref-meta: "
decl_stmt|;
DECL|field|SAVE_ORDER_CONFIG
specifier|public
specifier|static
specifier|final
name|String
name|SAVE_ORDER_CONFIG
init|=
literal|"saveOrderConfig"
decl_stmt|;
DECL|field|SAVE_ACTIONS
specifier|public
specifier|static
specifier|final
name|String
name|SAVE_ACTIONS
init|=
literal|"saveActions"
decl_stmt|;
DECL|field|PREFIX_KEYPATTERN
specifier|public
specifier|static
specifier|final
name|String
name|PREFIX_KEYPATTERN
init|=
literal|"keypattern_"
decl_stmt|;
DECL|field|KEYPATTERNDEFAULT
specifier|public
specifier|static
specifier|final
name|String
name|KEYPATTERNDEFAULT
init|=
literal|"keypatterndefault"
decl_stmt|;
DECL|field|DATABASE_TYPE
specifier|public
specifier|static
specifier|final
name|String
name|DATABASE_TYPE
init|=
literal|"databaseType"
decl_stmt|;
DECL|field|GROUPSTREE
specifier|public
specifier|static
specifier|final
name|String
name|GROUPSTREE
init|=
literal|"groupstree"
decl_stmt|;
DECL|field|FILE_DIRECTORY
specifier|public
specifier|static
specifier|final
name|String
name|FILE_DIRECTORY
init|=
name|FieldName
operator|.
name|FILE
operator|+
name|FileDirectoryPreferences
operator|.
name|DIR_SUFFIX
decl_stmt|;
DECL|field|PROTECTED_FLAG_META
specifier|public
specifier|static
specifier|final
name|String
name|PROTECTED_FLAG_META
init|=
literal|"protectedFlag"
decl_stmt|;
DECL|field|SELECTOR_META_PREFIX
specifier|public
specifier|static
specifier|final
name|String
name|SELECTOR_META_PREFIX
init|=
literal|"selector_"
decl_stmt|;
DECL|field|ESCAPE_CHARACTER
specifier|public
specifier|static
specifier|final
name|char
name|ESCAPE_CHARACTER
init|=
literal|'\\'
decl_stmt|;
DECL|field|SEPARATOR_CHARACTER
specifier|public
specifier|static
specifier|final
name|char
name|SEPARATOR_CHARACTER
init|=
literal|';'
decl_stmt|;
DECL|field|SEPARATOR_STRING
specifier|public
specifier|static
specifier|final
name|String
name|SEPARATOR_STRING
init|=
name|String
operator|.
name|valueOf
argument_list|(
name|SEPARATOR_CHARACTER
argument_list|)
decl_stmt|;
DECL|field|eventBus
specifier|private
specifier|final
name|EventBus
name|eventBus
init|=
operator|new
name|EventBus
argument_list|()
decl_stmt|;
DECL|field|groupsRoot
specifier|private
name|GroupTreeNode
name|groupsRoot
decl_stmt|;
DECL|field|encoding
specifier|private
name|Charset
name|encoding
decl_stmt|;
DECL|field|saveOrderConfig
specifier|private
name|SaveOrderConfig
name|saveOrderConfig
decl_stmt|;
DECL|field|citeKeyPatterns
specifier|private
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|citeKeyPatterns
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
comment|//<BibType, Pattern>
DECL|field|userFileDirectory
specifier|private
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|userFileDirectory
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
comment|//<User, FilePath>
DECL|field|defaultCiteKeyPattern
specifier|private
name|String
name|defaultCiteKeyPattern
decl_stmt|;
DECL|field|saveActions
specifier|private
name|FieldFormatterCleanups
name|saveActions
decl_stmt|;
DECL|field|mode
specifier|private
name|BibDatabaseMode
name|mode
decl_stmt|;
DECL|field|isProtected
specifier|private
name|boolean
name|isProtected
decl_stmt|;
DECL|field|defaultFileDirectory
specifier|private
name|String
name|defaultFileDirectory
decl_stmt|;
DECL|field|contentSelectors
specifier|private
name|ContentSelectors
name|contentSelectors
init|=
operator|new
name|ContentSelectors
argument_list|()
decl_stmt|;
comment|/**      * Constructs an empty metadata.      */
DECL|method|MetaData ()
specifier|public
name|MetaData
parameter_list|()
block|{
comment|// Do nothing
block|}
DECL|method|getSaveOrderConfig ()
specifier|public
name|Optional
argument_list|<
name|SaveOrderConfig
argument_list|>
name|getSaveOrderConfig
parameter_list|()
block|{
return|return
name|Optional
operator|.
name|ofNullable
argument_list|(
name|saveOrderConfig
argument_list|)
return|;
block|}
DECL|method|setSaveOrderConfig (SaveOrderConfig saveOrderConfig)
specifier|public
name|void
name|setSaveOrderConfig
parameter_list|(
name|SaveOrderConfig
name|saveOrderConfig
parameter_list|)
block|{
name|this
operator|.
name|saveOrderConfig
operator|=
name|saveOrderConfig
expr_stmt|;
name|postChange
argument_list|()
expr_stmt|;
block|}
DECL|method|getGroups ()
specifier|public
name|Optional
argument_list|<
name|GroupTreeNode
argument_list|>
name|getGroups
parameter_list|()
block|{
return|return
name|Optional
operator|.
name|ofNullable
argument_list|(
name|groupsRoot
argument_list|)
return|;
block|}
comment|/**      * Sets a new group root node.<b>WARNING</b>: This invalidates everything      * returned by getGroups() so far!!!      */
DECL|method|setGroups (GroupTreeNode root)
specifier|public
name|void
name|setGroups
parameter_list|(
name|GroupTreeNode
name|root
parameter_list|)
block|{
name|groupsRoot
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|root
argument_list|)
expr_stmt|;
name|groupsRoot
operator|.
name|subscribeToDescendantChanged
argument_list|(
name|groupTreeNode
lambda|->
name|eventBus
operator|.
name|post
argument_list|(
operator|new
name|GroupUpdatedEvent
argument_list|(
name|this
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|eventBus
operator|.
name|post
argument_list|(
operator|new
name|GroupUpdatedEvent
argument_list|(
name|this
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * @return the stored label patterns      */
DECL|method|getCiteKeyPattern (GlobalBibtexKeyPattern globalPattern)
specifier|public
name|AbstractBibtexKeyPattern
name|getCiteKeyPattern
parameter_list|(
name|GlobalBibtexKeyPattern
name|globalPattern
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|globalPattern
argument_list|)
expr_stmt|;
name|AbstractBibtexKeyPattern
name|bibtexKeyPattern
init|=
operator|new
name|DatabaseBibtexKeyPattern
argument_list|(
name|globalPattern
argument_list|)
decl_stmt|;
comment|// Add stored key patterns
name|citeKeyPatterns
operator|.
name|forEach
argument_list|(
name|bibtexKeyPattern
operator|::
name|addBibtexKeyPattern
argument_list|)
expr_stmt|;
name|getDefaultCiteKeyPattern
argument_list|()
operator|.
name|ifPresent
argument_list|(
name|bibtexKeyPattern
operator|::
name|setDefaultValue
argument_list|)
expr_stmt|;
return|return
name|bibtexKeyPattern
return|;
block|}
comment|/**      * Updates the stored key patterns to the given key patterns.      *      * @param bibtexKeyPattern the key patterns to update to.<br />      *                     A reference to this object is stored internally and is returned at getCiteKeyPattern();      */
DECL|method|setCiteKeyPattern (AbstractBibtexKeyPattern bibtexKeyPattern)
specifier|public
name|void
name|setCiteKeyPattern
parameter_list|(
name|AbstractBibtexKeyPattern
name|bibtexKeyPattern
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|bibtexKeyPattern
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|defaultValue
init|=
name|bibtexKeyPattern
operator|.
name|getDefaultValue
argument_list|()
decl_stmt|;
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|String
argument_list|>
argument_list|>
name|nonDefaultPatterns
init|=
name|bibtexKeyPattern
operator|.
name|getPatterns
argument_list|()
decl_stmt|;
name|setCiteKeyPattern
argument_list|(
name|defaultValue
argument_list|,
name|nonDefaultPatterns
argument_list|)
expr_stmt|;
block|}
DECL|method|setCiteKeyPattern (List<String> defaultValue, Map<String, List<String>> nonDefaultPatterns)
specifier|public
name|void
name|setCiteKeyPattern
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|defaultValue
parameter_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|String
argument_list|>
argument_list|>
name|nonDefaultPatterns
parameter_list|)
block|{
comment|// Remove all patterns from metadata
name|citeKeyPatterns
operator|.
name|clear
argument_list|()
expr_stmt|;
comment|// Set new value if it is not a default value
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|String
argument_list|>
argument_list|>
name|pattern
range|:
name|nonDefaultPatterns
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|citeKeyPatterns
operator|.
name|put
argument_list|(
name|pattern
operator|.
name|getKey
argument_list|()
argument_list|,
name|pattern
operator|.
name|getValue
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// Store default pattern
if|if
condition|(
name|defaultValue
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|defaultCiteKeyPattern
operator|=
literal|null
expr_stmt|;
block|}
else|else
block|{
name|defaultCiteKeyPattern
operator|=
name|defaultValue
operator|.
name|get
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
name|postChange
argument_list|()
expr_stmt|;
block|}
DECL|method|getSaveActions ()
specifier|public
name|Optional
argument_list|<
name|FieldFormatterCleanups
argument_list|>
name|getSaveActions
parameter_list|()
block|{
return|return
name|Optional
operator|.
name|ofNullable
argument_list|(
name|saveActions
argument_list|)
return|;
block|}
DECL|method|setSaveActions (FieldFormatterCleanups saveActions)
specifier|public
name|void
name|setSaveActions
parameter_list|(
name|FieldFormatterCleanups
name|saveActions
parameter_list|)
block|{
name|this
operator|.
name|saveActions
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|saveActions
argument_list|)
expr_stmt|;
name|postChange
argument_list|()
expr_stmt|;
block|}
DECL|method|getMode ()
specifier|public
name|Optional
argument_list|<
name|BibDatabaseMode
argument_list|>
name|getMode
parameter_list|()
block|{
return|return
name|Optional
operator|.
name|ofNullable
argument_list|(
name|mode
argument_list|)
return|;
block|}
DECL|method|setMode (BibDatabaseMode mode)
specifier|public
name|void
name|setMode
parameter_list|(
name|BibDatabaseMode
name|mode
parameter_list|)
block|{
name|this
operator|.
name|mode
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|mode
argument_list|)
expr_stmt|;
name|postChange
argument_list|()
expr_stmt|;
block|}
DECL|method|isProtected ()
specifier|public
name|boolean
name|isProtected
parameter_list|()
block|{
return|return
name|isProtected
return|;
block|}
DECL|method|getContentSelectors ()
specifier|public
name|Optional
argument_list|<
name|ContentSelectors
argument_list|>
name|getContentSelectors
parameter_list|()
block|{
return|return
name|Optional
operator|.
name|ofNullable
argument_list|(
name|contentSelectors
argument_list|)
return|;
block|}
DECL|method|addContentSelector (ContentSelector contentSelector)
specifier|public
name|void
name|addContentSelector
parameter_list|(
name|ContentSelector
name|contentSelector
parameter_list|)
block|{
name|this
operator|.
name|contentSelectors
operator|.
name|addContentSelector
argument_list|(
name|contentSelector
argument_list|)
expr_stmt|;
name|postChange
argument_list|()
expr_stmt|;
block|}
DECL|method|clearContentSelectors (String fieldName)
specifier|public
name|void
name|clearContentSelectors
parameter_list|(
name|String
name|fieldName
parameter_list|)
block|{
name|contentSelectors
operator|.
name|removeSelector
argument_list|(
name|fieldName
argument_list|)
expr_stmt|;
block|}
DECL|method|getContentSelectors (String fieldName)
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getContentSelectors
parameter_list|(
name|String
name|fieldName
parameter_list|)
block|{
return|return
name|contentSelectors
operator|.
name|getSelectorValuesForField
argument_list|(
name|fieldName
argument_list|)
return|;
block|}
DECL|method|getDefaultFileDirectory ()
specifier|public
name|Optional
argument_list|<
name|String
argument_list|>
name|getDefaultFileDirectory
parameter_list|()
block|{
return|return
name|Optional
operator|.
name|ofNullable
argument_list|(
name|defaultFileDirectory
argument_list|)
return|;
block|}
DECL|method|setDefaultFileDirectory (String path)
specifier|public
name|void
name|setDefaultFileDirectory
parameter_list|(
name|String
name|path
parameter_list|)
block|{
name|defaultFileDirectory
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|path
argument_list|)
operator|.
name|trim
argument_list|()
expr_stmt|;
name|postChange
argument_list|()
expr_stmt|;
block|}
DECL|method|getUserFileDirectory (String user)
specifier|public
name|Optional
argument_list|<
name|String
argument_list|>
name|getUserFileDirectory
parameter_list|(
name|String
name|user
parameter_list|)
block|{
return|return
name|Optional
operator|.
name|ofNullable
argument_list|(
name|userFileDirectory
operator|.
name|get
argument_list|(
name|user
argument_list|)
argument_list|)
return|;
block|}
DECL|method|markAsProtected ()
specifier|public
name|void
name|markAsProtected
parameter_list|()
block|{
name|isProtected
operator|=
literal|true
expr_stmt|;
name|postChange
argument_list|()
expr_stmt|;
block|}
DECL|method|clearDefaultFileDirectory ()
specifier|public
name|void
name|clearDefaultFileDirectory
parameter_list|()
block|{
name|defaultFileDirectory
operator|=
literal|null
expr_stmt|;
name|postChange
argument_list|()
expr_stmt|;
block|}
DECL|method|setUserFileDirectory (String user, String path)
specifier|public
name|void
name|setUserFileDirectory
parameter_list|(
name|String
name|user
parameter_list|,
name|String
name|path
parameter_list|)
block|{
name|userFileDirectory
operator|.
name|put
argument_list|(
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|user
argument_list|)
argument_list|,
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|path
argument_list|)
argument_list|)
expr_stmt|;
name|postChange
argument_list|()
expr_stmt|;
block|}
DECL|method|clearUserFileDirectory (String user)
specifier|public
name|void
name|clearUserFileDirectory
parameter_list|(
name|String
name|user
parameter_list|)
block|{
name|userFileDirectory
operator|.
name|remove
argument_list|(
name|user
argument_list|)
expr_stmt|;
name|postChange
argument_list|()
expr_stmt|;
block|}
DECL|method|markAsNotProtected ()
specifier|public
name|void
name|markAsNotProtected
parameter_list|()
block|{
name|isProtected
operator|=
literal|false
expr_stmt|;
name|postChange
argument_list|()
expr_stmt|;
block|}
DECL|method|clearSaveActions ()
specifier|public
name|void
name|clearSaveActions
parameter_list|()
block|{
name|saveActions
operator|=
literal|null
expr_stmt|;
name|postChange
argument_list|()
expr_stmt|;
block|}
DECL|method|clearSaveOrderConfig ()
specifier|public
name|void
name|clearSaveOrderConfig
parameter_list|()
block|{
name|saveOrderConfig
operator|=
literal|null
expr_stmt|;
name|postChange
argument_list|()
expr_stmt|;
block|}
comment|/**      * Posts a new {@link MetaDataChangedEvent} on the {@link EventBus}.      */
DECL|method|postChange ()
specifier|private
name|void
name|postChange
parameter_list|()
block|{
name|eventBus
operator|.
name|post
argument_list|(
operator|new
name|MetaDataChangedEvent
argument_list|(
name|this
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * Returns the encoding used during parsing.      */
DECL|method|getEncoding ()
specifier|public
name|Optional
argument_list|<
name|Charset
argument_list|>
name|getEncoding
parameter_list|()
block|{
return|return
name|Optional
operator|.
name|ofNullable
argument_list|(
name|encoding
argument_list|)
return|;
block|}
DECL|method|setEncoding (Charset encoding)
specifier|public
name|void
name|setEncoding
parameter_list|(
name|Charset
name|encoding
parameter_list|)
block|{
name|setEncoding
argument_list|(
name|encoding
argument_list|,
name|ChangePropagation
operator|.
name|POST_EVENT
argument_list|)
expr_stmt|;
block|}
comment|/**      * This Method (with additional parameter) has been introduced to avoid event loops while saving a database.      */
DECL|method|setEncoding (Charset encoding, ChangePropagation postChanges)
specifier|public
name|void
name|setEncoding
parameter_list|(
name|Charset
name|encoding
parameter_list|,
name|ChangePropagation
name|postChanges
parameter_list|)
block|{
name|this
operator|.
name|encoding
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|encoding
argument_list|)
expr_stmt|;
if|if
condition|(
name|postChanges
operator|==
name|ChangePropagation
operator|.
name|POST_EVENT
condition|)
block|{
name|postChange
argument_list|()
expr_stmt|;
block|}
block|}
DECL|method|registerListener (Object listener)
specifier|public
name|void
name|registerListener
parameter_list|(
name|Object
name|listener
parameter_list|)
block|{
name|this
operator|.
name|eventBus
operator|.
name|register
argument_list|(
name|listener
argument_list|)
expr_stmt|;
block|}
DECL|method|unregisterListener (Object listener)
specifier|public
name|void
name|unregisterListener
parameter_list|(
name|Object
name|listener
parameter_list|)
block|{
name|this
operator|.
name|eventBus
operator|.
name|unregister
argument_list|(
name|listener
argument_list|)
expr_stmt|;
block|}
DECL|method|getDefaultCiteKeyPattern ()
specifier|private
name|Optional
argument_list|<
name|String
argument_list|>
name|getDefaultCiteKeyPattern
parameter_list|()
block|{
return|return
name|Optional
operator|.
name|ofNullable
argument_list|(
name|defaultCiteKeyPattern
argument_list|)
return|;
block|}
DECL|method|isEmpty ()
specifier|public
name|boolean
name|isEmpty
parameter_list|()
block|{
return|return
name|this
operator|.
name|equals
argument_list|(
operator|new
name|MetaData
argument_list|()
argument_list|)
return|;
block|}
DECL|method|getUserFileDirectories ()
specifier|public
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|getUserFileDirectories
parameter_list|()
block|{
return|return
name|Collections
operator|.
name|unmodifiableMap
argument_list|(
name|userFileDirectory
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|equals (Object o)
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
if|if
condition|(
name|this
operator|==
name|o
condition|)
block|{
return|return
literal|true
return|;
block|}
if|if
condition|(
operator|(
name|o
operator|==
literal|null
operator|)
operator|||
operator|(
name|getClass
argument_list|()
operator|!=
name|o
operator|.
name|getClass
argument_list|()
operator|)
condition|)
block|{
return|return
literal|false
return|;
block|}
name|MetaData
name|metaData
init|=
operator|(
name|MetaData
operator|)
name|o
decl_stmt|;
return|return
operator|(
name|isProtected
operator|==
name|metaData
operator|.
name|isProtected
operator|)
operator|&&
name|Objects
operator|.
name|equals
argument_list|(
name|groupsRoot
argument_list|,
name|metaData
operator|.
name|groupsRoot
argument_list|)
operator|&&
name|Objects
operator|.
name|equals
argument_list|(
name|encoding
argument_list|,
name|metaData
operator|.
name|encoding
argument_list|)
operator|&&
name|Objects
operator|.
name|equals
argument_list|(
name|saveOrderConfig
argument_list|,
name|metaData
operator|.
name|saveOrderConfig
argument_list|)
operator|&&
name|Objects
operator|.
name|equals
argument_list|(
name|citeKeyPatterns
argument_list|,
name|metaData
operator|.
name|citeKeyPatterns
argument_list|)
operator|&&
name|Objects
operator|.
name|equals
argument_list|(
name|userFileDirectory
argument_list|,
name|metaData
operator|.
name|userFileDirectory
argument_list|)
operator|&&
name|Objects
operator|.
name|equals
argument_list|(
name|defaultCiteKeyPattern
argument_list|,
name|metaData
operator|.
name|defaultCiteKeyPattern
argument_list|)
operator|&&
name|Objects
operator|.
name|equals
argument_list|(
name|saveActions
argument_list|,
name|metaData
operator|.
name|saveActions
argument_list|)
operator|&&
operator|(
name|mode
operator|==
name|metaData
operator|.
name|mode
operator|)
operator|&&
name|Objects
operator|.
name|equals
argument_list|(
name|defaultFileDirectory
argument_list|,
name|metaData
operator|.
name|defaultFileDirectory
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
name|groupsRoot
argument_list|,
name|encoding
argument_list|,
name|saveOrderConfig
argument_list|,
name|citeKeyPatterns
argument_list|,
name|userFileDirectory
argument_list|,
name|defaultCiteKeyPattern
argument_list|,
name|saveActions
argument_list|,
name|mode
argument_list|,
name|isProtected
argument_list|,
name|defaultFileDirectory
argument_list|)
return|;
block|}
block|}
end_class

end_unit

