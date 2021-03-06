begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.model.bibtexkeypattern
package|package
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|bibtexkeypattern
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
name|Set
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|StringTokenizer
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
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|types
operator|.
name|EntryType
import|;
end_import

begin_comment
comment|/**  * A small table, where an entry type is associated with a Bibtex key pattern (an  *<code>ArrayList</code>). A parent BibtexKeyPattern can be set.  */
end_comment

begin_class
DECL|class|AbstractBibtexKeyPattern
specifier|public
specifier|abstract
class|class
name|AbstractBibtexKeyPattern
block|{
DECL|field|defaultPattern
specifier|protected
name|List
argument_list|<
name|String
argument_list|>
name|defaultPattern
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|data
specifier|protected
name|Map
argument_list|<
name|EntryType
argument_list|,
name|List
argument_list|<
name|String
argument_list|>
argument_list|>
name|data
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
DECL|method|addBibtexKeyPattern (EntryType type, String pattern)
specifier|public
name|void
name|addBibtexKeyPattern
parameter_list|(
name|EntryType
name|type
parameter_list|,
name|String
name|pattern
parameter_list|)
block|{
name|data
operator|.
name|put
argument_list|(
name|type
argument_list|,
name|AbstractBibtexKeyPattern
operator|.
name|split
argument_list|(
name|pattern
argument_list|)
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
specifier|final
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|(
literal|"AbstractBibtexKeyPattern{"
argument_list|)
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"defaultPattern="
argument_list|)
operator|.
name|append
argument_list|(
name|defaultPattern
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|", data="
argument_list|)
operator|.
name|append
argument_list|(
name|data
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|'}'
argument_list|)
expr_stmt|;
return|return
name|sb
operator|.
name|toString
argument_list|()
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
name|AbstractBibtexKeyPattern
name|that
init|=
operator|(
name|AbstractBibtexKeyPattern
operator|)
name|o
decl_stmt|;
return|return
name|Objects
operator|.
name|equals
argument_list|(
name|defaultPattern
argument_list|,
name|that
operator|.
name|defaultPattern
argument_list|)
operator|&&
name|Objects
operator|.
name|equals
argument_list|(
name|data
argument_list|,
name|that
operator|.
name|data
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
name|defaultPattern
argument_list|,
name|data
argument_list|)
return|;
block|}
comment|/**      * Gets an object for a desired key from this BibtexKeyPattern or one of it's      * parents (in the case of DatabaseBibtexKeyPattern). This method first tries to obtain the object from this      * BibtexKeyPattern via the<code>get</code> method of<code>Hashtable</code>.      * If this fails, we try the default.<br />      * If that fails, we try the parent.<br />      * If that fails, we return the DEFAULT_LABELPATTERN<br />      *      * @param entryType a<code>String</code>      * @return the list of Strings for the given key. First entry: the complete key      */
DECL|method|getValue (EntryType entryType)
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getValue
parameter_list|(
name|EntryType
name|entryType
parameter_list|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|result
init|=
name|data
operator|.
name|get
argument_list|(
name|entryType
argument_list|)
decl_stmt|;
comment|//  Test to see if we found anything
if|if
condition|(
name|result
operator|==
literal|null
condition|)
block|{
comment|// check default value
name|result
operator|=
name|getDefaultValue
argument_list|()
expr_stmt|;
if|if
condition|(
name|result
operator|==
literal|null
operator|||
name|result
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
comment|// we are the "last" to ask
comment|// we don't have anything left
return|return
name|getLastLevelBibtexKeyPattern
argument_list|(
name|entryType
argument_list|)
return|;
block|}
block|}
return|return
name|result
return|;
block|}
comment|/**      * This method takes a string of the form [field1]spacer[field2]spacer[field3]...,      * where the fields are the (required) fields of a BibTex entry. The string is split      * into fields and spacers by recognizing the [ and ].      *      * @param bibtexKeyPattern a<code>String</code>      * @return an<code>ArrayList</code> The first item of the list      * is a string representation of the key pattern (the parameter),      * the remaining items are the fields      */
DECL|method|split (String bibtexKeyPattern)
specifier|public
specifier|static
name|List
argument_list|<
name|String
argument_list|>
name|split
parameter_list|(
name|String
name|bibtexKeyPattern
parameter_list|)
block|{
comment|// A holder for fields of the entry to be used for the key
name|List
argument_list|<
name|String
argument_list|>
name|fieldList
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
comment|// Before we do anything, we add the parameter to the ArrayLIst
name|fieldList
operator|.
name|add
argument_list|(
name|bibtexKeyPattern
argument_list|)
expr_stmt|;
name|StringTokenizer
name|tok
init|=
operator|new
name|StringTokenizer
argument_list|(
name|bibtexKeyPattern
argument_list|,
literal|"[]"
argument_list|,
literal|true
argument_list|)
decl_stmt|;
while|while
condition|(
name|tok
operator|.
name|hasMoreTokens
argument_list|()
condition|)
block|{
name|fieldList
operator|.
name|add
argument_list|(
name|tok
operator|.
name|nextToken
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|fieldList
return|;
block|}
comment|/**      * Checks whether this pattern is customized or the default value.      */
DECL|method|isDefaultValue (EntryType entryType)
specifier|public
specifier|final
name|boolean
name|isDefaultValue
parameter_list|(
name|EntryType
name|entryType
parameter_list|)
block|{
return|return
name|data
operator|.
name|get
argument_list|(
name|entryType
argument_list|)
operator|==
literal|null
return|;
block|}
comment|/**      * This method is called "...Value" to be in line with the other methods      *      * @return null if not available.      */
DECL|method|getDefaultValue ()
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getDefaultValue
parameter_list|()
block|{
return|return
name|this
operator|.
name|defaultPattern
return|;
block|}
comment|/**      * Sets the DEFAULT PATTERN for this key pattern      * @param bibtexKeyPattern the pattern to store      */
DECL|method|setDefaultValue (String bibtexKeyPattern)
specifier|public
name|void
name|setDefaultValue
parameter_list|(
name|String
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
name|this
operator|.
name|defaultPattern
operator|=
name|AbstractBibtexKeyPattern
operator|.
name|split
argument_list|(
name|bibtexKeyPattern
argument_list|)
expr_stmt|;
block|}
DECL|method|getAllKeys ()
specifier|public
name|Set
argument_list|<
name|EntryType
argument_list|>
name|getAllKeys
parameter_list|()
block|{
return|return
name|data
operator|.
name|keySet
argument_list|()
return|;
block|}
DECL|method|getPatterns ()
specifier|public
name|Map
argument_list|<
name|EntryType
argument_list|,
name|List
argument_list|<
name|String
argument_list|>
argument_list|>
name|getPatterns
parameter_list|()
block|{
return|return
name|data
operator|.
name|entrySet
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toMap
argument_list|(
name|Map
operator|.
name|Entry
operator|::
name|getKey
argument_list|,
name|Map
operator|.
name|Entry
operator|::
name|getValue
argument_list|)
argument_list|)
return|;
block|}
DECL|method|getLastLevelBibtexKeyPattern (EntryType key)
specifier|public
specifier|abstract
name|List
argument_list|<
name|String
argument_list|>
name|getLastLevelBibtexKeyPattern
parameter_list|(
name|EntryType
name|key
parameter_list|)
function_decl|;
block|}
end_class

end_unit

