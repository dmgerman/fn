begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.model
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
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
name|entry
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
name|*
import|;
end_import

begin_class
DECL|class|EntryTypes
specifier|public
class|class
name|EntryTypes
block|{
comment|/**      * This class is used to specify entry types for either BIBTEX and BIBLATEX.      */
DECL|class|InternalEntryTypes
specifier|private
specifier|static
class|class
name|InternalEntryTypes
block|{
DECL|field|ALL_TYPES
specifier|private
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|EntryType
argument_list|>
name|ALL_TYPES
init|=
operator|new
name|TreeMap
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|STANDARD_TYPES
specifier|private
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|EntryType
argument_list|>
name|STANDARD_TYPES
decl_stmt|;
DECL|field|defaultType
specifier|private
specifier|final
name|EntryType
name|defaultType
decl_stmt|;
DECL|method|InternalEntryTypes (EntryType defaultType, List<EntryType>... entryTypes)
specifier|public
name|InternalEntryTypes
parameter_list|(
name|EntryType
name|defaultType
parameter_list|,
name|List
argument_list|<
name|EntryType
argument_list|>
modifier|...
name|entryTypes
parameter_list|)
block|{
name|this
operator|.
name|defaultType
operator|=
name|defaultType
expr_stmt|;
for|for
control|(
name|List
argument_list|<
name|EntryType
argument_list|>
name|list
range|:
name|entryTypes
control|)
block|{
for|for
control|(
name|EntryType
name|type
range|:
name|list
control|)
block|{
name|ALL_TYPES
operator|.
name|put
argument_list|(
name|type
operator|.
name|getName
argument_list|()
operator|.
name|toLowerCase
argument_list|()
argument_list|,
name|type
argument_list|)
expr_stmt|;
block|}
block|}
name|STANDARD_TYPES
operator|=
operator|new
name|TreeMap
argument_list|<>
argument_list|(
name|ALL_TYPES
argument_list|)
expr_stmt|;
block|}
comment|/**          * This method returns the BibtexEntryType for the name of a type,          * or null if it does not exist.          */
DECL|method|getType (String name)
specifier|public
name|Optional
argument_list|<
name|EntryType
argument_list|>
name|getType
parameter_list|(
name|String
name|name
parameter_list|)
block|{
return|return
name|Optional
operator|.
name|ofNullable
argument_list|(
name|ALL_TYPES
operator|.
name|get
argument_list|(
name|name
operator|.
name|toLowerCase
argument_list|()
argument_list|)
argument_list|)
return|;
block|}
comment|/**          * This method returns the EntryType for the name of a type,          * or the default type (*.MISC) if it does not exist.          */
comment|// Get an entry type defined in BibtexEntryType
DECL|method|getTypeOrDefault (String type)
specifier|public
name|EntryType
name|getTypeOrDefault
parameter_list|(
name|String
name|type
parameter_list|)
block|{
return|return
name|getType
argument_list|(
name|type
argument_list|)
operator|.
name|orElse
argument_list|(
name|defaultType
argument_list|)
return|;
block|}
comment|/**          * This method returns the standard BibtexEntryType for the          * name of a type, or null if it does not exist.          */
DECL|method|getStandardType (String name)
specifier|public
name|Optional
argument_list|<
name|EntryType
argument_list|>
name|getStandardType
parameter_list|(
name|String
name|name
parameter_list|)
block|{
return|return
name|Optional
operator|.
name|ofNullable
argument_list|(
name|STANDARD_TYPES
operator|.
name|get
argument_list|(
name|name
operator|.
name|toLowerCase
argument_list|()
argument_list|)
argument_list|)
return|;
block|}
DECL|method|addOrModifyCustomEntryType (CustomEntryType type)
specifier|public
name|void
name|addOrModifyCustomEntryType
parameter_list|(
name|CustomEntryType
name|type
parameter_list|)
block|{
name|addOrModifyEntryType
argument_list|(
name|type
argument_list|)
expr_stmt|;
block|}
DECL|method|addOrModifyEntryType (EntryType type)
specifier|private
name|void
name|addOrModifyEntryType
parameter_list|(
name|EntryType
name|type
parameter_list|)
block|{
name|ALL_TYPES
operator|.
name|put
argument_list|(
name|type
operator|.
name|getName
argument_list|()
operator|.
name|toLowerCase
argument_list|()
argument_list|,
name|type
argument_list|)
expr_stmt|;
block|}
DECL|method|getAllTypes ()
specifier|public
name|Set
argument_list|<
name|String
argument_list|>
name|getAllTypes
parameter_list|()
block|{
return|return
name|ALL_TYPES
operator|.
name|keySet
argument_list|()
return|;
block|}
DECL|method|getAllValues ()
specifier|public
name|Collection
argument_list|<
name|EntryType
argument_list|>
name|getAllValues
parameter_list|()
block|{
return|return
name|ALL_TYPES
operator|.
name|values
argument_list|()
return|;
block|}
comment|/**          * Removes a customized entry type from the type map. If this type          * overrode a standard type, we reinstate the standard one.          *          * @param name The customized entry type to remove.          */
DECL|method|removeType (String name)
specifier|public
name|void
name|removeType
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|String
name|toLowerCase
init|=
name|name
operator|.
name|toLowerCase
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|ALL_TYPES
operator|.
name|get
argument_list|(
name|toLowerCase
argument_list|)
operator|.
name|equals
argument_list|(
name|STANDARD_TYPES
operator|.
name|get
argument_list|(
name|toLowerCase
argument_list|)
argument_list|)
condition|)
block|{
name|ALL_TYPES
operator|.
name|remove
argument_list|(
name|toLowerCase
argument_list|)
expr_stmt|;
if|if
condition|(
name|STANDARD_TYPES
operator|.
name|get
argument_list|(
name|toLowerCase
argument_list|)
operator|!=
literal|null
condition|)
block|{
comment|// In this case the user has removed a customized version
comment|// of a standard type. We reinstate the standard type.
name|addOrModifyEntryType
argument_list|(
name|STANDARD_TYPES
operator|.
name|get
argument_list|(
name|toLowerCase
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
DECL|field|BIBTEX
specifier|public
specifier|static
specifier|final
name|InternalEntryTypes
name|BIBTEX
init|=
operator|new
name|InternalEntryTypes
argument_list|(
name|BibtexEntryTypes
operator|.
name|MISC
argument_list|,
name|BibtexEntryTypes
operator|.
name|ALL
argument_list|,
name|IEEETranEntryTypes
operator|.
name|ALL
argument_list|)
decl_stmt|;
DECL|field|BIBLATEX
specifier|public
specifier|static
specifier|final
name|InternalEntryTypes
name|BIBLATEX
init|=
operator|new
name|InternalEntryTypes
argument_list|(
name|BibLatexEntryTypes
operator|.
name|MISC
argument_list|,
name|BibLatexEntryTypes
operator|.
name|ALL
argument_list|)
decl_stmt|;
comment|/**      * This method returns the BibtexEntryType for the name of a type,      * or null if it does not exist.      */
DECL|method|getType (String name, BibDatabaseMode type)
specifier|public
specifier|static
name|Optional
argument_list|<
name|EntryType
argument_list|>
name|getType
parameter_list|(
name|String
name|name
parameter_list|,
name|BibDatabaseMode
name|type
parameter_list|)
block|{
return|return
name|type
operator|==
name|BibDatabaseMode
operator|.
name|BIBLATEX
condition|?
name|BIBLATEX
operator|.
name|getType
argument_list|(
name|name
argument_list|)
else|:
name|BIBTEX
operator|.
name|getType
argument_list|(
name|name
argument_list|)
return|;
block|}
comment|/**      * This method returns the EntryType for the name of a type,      * or the default type (*.MISC) if it does not exist.      */
comment|// Get an entry type defined in BibtexEntryType
DECL|method|getTypeOrDefault (String name, BibDatabaseMode mode)
specifier|public
specifier|static
name|EntryType
name|getTypeOrDefault
parameter_list|(
name|String
name|name
parameter_list|,
name|BibDatabaseMode
name|mode
parameter_list|)
block|{
return|return
name|mode
operator|==
name|BibDatabaseMode
operator|.
name|BIBLATEX
condition|?
name|BIBLATEX
operator|.
name|getTypeOrDefault
argument_list|(
name|name
argument_list|)
else|:
name|BIBTEX
operator|.
name|getTypeOrDefault
argument_list|(
name|name
argument_list|)
return|;
block|}
comment|/**      * This method returns the standard BibtexEntryType for the      * name of a type, or null if it does not exist.      */
DECL|method|getStandardType (String name, BibDatabaseMode mode)
specifier|public
specifier|static
name|Optional
argument_list|<
name|EntryType
argument_list|>
name|getStandardType
parameter_list|(
name|String
name|name
parameter_list|,
name|BibDatabaseMode
name|mode
parameter_list|)
block|{
return|return
name|mode
operator|==
name|BibDatabaseMode
operator|.
name|BIBLATEX
condition|?
name|BIBLATEX
operator|.
name|getStandardType
argument_list|(
name|name
argument_list|)
else|:
name|BIBTEX
operator|.
name|getStandardType
argument_list|(
name|name
argument_list|)
return|;
block|}
DECL|method|addOrModifyCustomEntryType (CustomEntryType customEntryType)
specifier|public
specifier|static
name|void
name|addOrModifyCustomEntryType
parameter_list|(
name|CustomEntryType
name|customEntryType
parameter_list|)
block|{
name|addOrModifyEntryType
argument_list|(
name|customEntryType
argument_list|)
expr_stmt|;
block|}
DECL|method|addOrModifyEntryType (EntryType name)
specifier|private
specifier|static
name|void
name|addOrModifyEntryType
parameter_list|(
name|EntryType
name|name
parameter_list|)
block|{
name|BIBLATEX
operator|.
name|addOrModifyEntryType
argument_list|(
name|name
argument_list|)
expr_stmt|;
name|BIBTEX
operator|.
name|addOrModifyEntryType
argument_list|(
name|name
argument_list|)
expr_stmt|;
block|}
DECL|method|getAllTypes (BibDatabaseMode type)
specifier|public
specifier|static
name|Set
argument_list|<
name|String
argument_list|>
name|getAllTypes
parameter_list|(
name|BibDatabaseMode
name|type
parameter_list|)
block|{
return|return
name|type
operator|==
name|BibDatabaseMode
operator|.
name|BIBLATEX
condition|?
name|BIBLATEX
operator|.
name|getAllTypes
argument_list|()
else|:
name|BIBTEX
operator|.
name|getAllTypes
argument_list|()
return|;
block|}
DECL|method|getAllValues (BibDatabaseMode type)
specifier|public
specifier|static
name|Collection
argument_list|<
name|EntryType
argument_list|>
name|getAllValues
parameter_list|(
name|BibDatabaseMode
name|type
parameter_list|)
block|{
return|return
name|type
operator|==
name|BibDatabaseMode
operator|.
name|BIBLATEX
condition|?
name|BIBLATEX
operator|.
name|getAllValues
argument_list|()
else|:
name|BIBTEX
operator|.
name|getAllValues
argument_list|()
return|;
block|}
comment|/**      * Removes a customized entry type from the type map. If this type      * overrode a standard type, we reinstate the standard one.      *      * @param name The customized entry type to remove.      */
DECL|method|removeType (String name, BibDatabaseMode type)
specifier|public
specifier|static
name|void
name|removeType
parameter_list|(
name|String
name|name
parameter_list|,
name|BibDatabaseMode
name|type
parameter_list|)
block|{
if|if
condition|(
name|type
operator|==
name|BibDatabaseMode
operator|.
name|BIBLATEX
condition|)
block|{
name|BIBLATEX
operator|.
name|removeType
argument_list|(
name|name
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|BIBTEX
operator|.
name|removeType
argument_list|(
name|name
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit
