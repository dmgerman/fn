begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.util
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
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
name|javax
operator|.
name|xml
operator|.
name|transform
operator|.
name|TransformerException
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
name|org
operator|.
name|jempbox
operator|.
name|xmp
operator|.
name|XMPMetadata
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jempbox
operator|.
name|xmp
operator|.
name|XMPSchema
import|;
end_import

begin_import
import|import
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Element
import|;
end_import

begin_import
import|import
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|NamedNodeMap
import|;
end_import

begin_import
import|import
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Node
import|;
end_import

begin_import
import|import
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|NodeList
import|;
end_import

begin_class
DECL|class|XMPSchemaBibtex
specifier|public
class|class
name|XMPSchemaBibtex
extends|extends
name|XMPSchema
block|{
comment|/** 	 * The namespace of this schema. 	 */
DECL|field|NAMESPACE
specifier|public
specifier|static
specifier|final
name|String
name|NAMESPACE
init|=
literal|"http://jabref.sourceforge.net/bibteXMP/"
decl_stmt|;
DECL|field|KEY
specifier|public
specifier|static
specifier|final
name|String
name|KEY
init|=
literal|"bibtex"
decl_stmt|;
comment|/** 	 * Create a new empty XMPSchemaBibtex as a child in the given XMPMetadata. 	 *  	 * @param parent 	 */
DECL|method|XMPSchemaBibtex (XMPMetadata parent)
specifier|public
name|XMPSchemaBibtex
parameter_list|(
name|XMPMetadata
name|parent
parameter_list|)
block|{
name|super
argument_list|(
name|parent
argument_list|,
name|KEY
argument_list|,
name|NAMESPACE
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * Create schema from an existing XML element. 	 *  	 * @param element 	 *            The existing XML element. 	 */
DECL|method|XMPSchemaBibtex (Element e, String namespace)
specifier|public
name|XMPSchemaBibtex
parameter_list|(
name|Element
name|e
parameter_list|,
name|String
name|namespace
parameter_list|)
block|{
name|super
argument_list|(
name|e
argument_list|,
name|KEY
argument_list|)
expr_stmt|;
block|}
DECL|method|makeProperty (String propertyName)
specifier|protected
name|String
name|makeProperty
parameter_list|(
name|String
name|propertyName
parameter_list|)
block|{
return|return
name|KEY
operator|+
literal|":"
operator|+
name|propertyName
return|;
block|}
comment|/** 	 *  	 * @param field 	 * @return 	 * @derived Uses XMPSchema methods 	 */
DECL|method|getPersonList (String field)
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getPersonList
parameter_list|(
name|String
name|field
parameter_list|)
block|{
return|return
name|getSequenceList
argument_list|(
name|field
argument_list|)
return|;
block|}
comment|/** 	 *  	 * @param field 	 * @param value 	 * @derived Uses XMPSchema methods 	 */
DECL|method|setPersonList (String field, String value)
specifier|public
name|void
name|setPersonList
parameter_list|(
name|String
name|field
parameter_list|,
name|String
name|value
parameter_list|)
block|{
name|AuthorList
name|list
init|=
name|AuthorList
operator|.
name|getAuthorList
argument_list|(
name|value
argument_list|)
decl_stmt|;
name|int
name|n
init|=
name|list
operator|.
name|size
argument_list|()
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
name|n
condition|;
name|i
operator|++
control|)
block|{
name|addSequenceValue
argument_list|(
name|field
argument_list|,
name|list
operator|.
name|getAuthor
argument_list|(
name|i
argument_list|)
operator|.
name|getFirstLast
argument_list|(
literal|false
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|getTextProperty (String field)
specifier|public
name|String
name|getTextProperty
parameter_list|(
name|String
name|field
parameter_list|)
block|{
return|return
name|super
operator|.
name|getTextProperty
argument_list|(
name|makeProperty
argument_list|(
name|field
argument_list|)
argument_list|)
return|;
block|}
DECL|method|setTextProperty (String field, String value)
specifier|public
name|void
name|setTextProperty
parameter_list|(
name|String
name|field
parameter_list|,
name|String
name|value
parameter_list|)
block|{
name|super
operator|.
name|setTextProperty
argument_list|(
name|makeProperty
argument_list|(
name|field
argument_list|)
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
DECL|method|getBagList (String bagName)
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getBagList
parameter_list|(
name|String
name|bagName
parameter_list|)
block|{
return|return
name|super
operator|.
name|getBagList
argument_list|(
name|makeProperty
argument_list|(
name|bagName
argument_list|)
argument_list|)
return|;
block|}
DECL|method|removeBagValue (String bagName, String value)
specifier|public
name|void
name|removeBagValue
parameter_list|(
name|String
name|bagName
parameter_list|,
name|String
name|value
parameter_list|)
block|{
name|super
operator|.
name|removeBagValue
argument_list|(
name|makeProperty
argument_list|(
name|bagName
argument_list|)
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
DECL|method|addBagValue (String bagName, String value)
specifier|public
name|void
name|addBagValue
parameter_list|(
name|String
name|bagName
parameter_list|,
name|String
name|value
parameter_list|)
block|{
name|super
operator|.
name|addBagValue
argument_list|(
name|makeProperty
argument_list|(
name|bagName
argument_list|)
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
DECL|method|getSequenceList (String seqName)
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getSequenceList
parameter_list|(
name|String
name|seqName
parameter_list|)
block|{
return|return
name|super
operator|.
name|getSequenceList
argument_list|(
name|makeProperty
argument_list|(
name|seqName
argument_list|)
argument_list|)
return|;
block|}
DECL|method|removeSequenceValue (String seqName, String value)
specifier|public
name|void
name|removeSequenceValue
parameter_list|(
name|String
name|seqName
parameter_list|,
name|String
name|value
parameter_list|)
block|{
name|super
operator|.
name|removeSequenceValue
argument_list|(
name|makeProperty
argument_list|(
name|seqName
argument_list|)
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
DECL|method|addSequenceValue (String seqName, String value)
specifier|public
name|void
name|addSequenceValue
parameter_list|(
name|String
name|seqName
parameter_list|,
name|String
name|value
parameter_list|)
block|{
name|super
operator|.
name|addSequenceValue
argument_list|(
name|makeProperty
argument_list|(
name|seqName
argument_list|)
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
DECL|method|getSequenceDateList (String seqName)
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getSequenceDateList
parameter_list|(
name|String
name|seqName
parameter_list|)
throws|throws
name|IOException
block|{
return|return
name|super
operator|.
name|getSequenceDateList
argument_list|(
name|makeProperty
argument_list|(
name|seqName
argument_list|)
argument_list|)
return|;
block|}
DECL|method|removeSequenceDateValue (String seqName, Calendar date)
specifier|public
name|void
name|removeSequenceDateValue
parameter_list|(
name|String
name|seqName
parameter_list|,
name|Calendar
name|date
parameter_list|)
block|{
name|super
operator|.
name|removeSequenceDateValue
argument_list|(
name|makeProperty
argument_list|(
name|seqName
argument_list|)
argument_list|,
name|date
argument_list|)
expr_stmt|;
block|}
DECL|method|addSequenceDateValue (String field, Calendar date)
specifier|public
name|void
name|addSequenceDateValue
parameter_list|(
name|String
name|field
parameter_list|,
name|Calendar
name|date
parameter_list|)
block|{
name|super
operator|.
name|addSequenceDateValue
argument_list|(
name|makeProperty
argument_list|(
name|field
argument_list|)
argument_list|,
name|date
argument_list|)
expr_stmt|;
block|}
DECL|method|getContents (NodeList seqList)
specifier|public
specifier|static
name|String
name|getContents
parameter_list|(
name|NodeList
name|seqList
parameter_list|)
block|{
name|Element
name|seqNode
init|=
operator|(
name|Element
operator|)
name|seqList
operator|.
name|item
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|StringBuffer
name|seq
init|=
literal|null
decl_stmt|;
name|NodeList
name|items
init|=
name|seqNode
operator|.
name|getElementsByTagName
argument_list|(
literal|"rdf:li"
argument_list|)
decl_stmt|;
for|for
control|(
name|int
name|j
init|=
literal|0
init|;
name|j
operator|<
name|items
operator|.
name|getLength
argument_list|()
condition|;
name|j
operator|++
control|)
block|{
name|Element
name|li
init|=
operator|(
name|Element
operator|)
name|items
operator|.
name|item
argument_list|(
name|j
argument_list|)
decl_stmt|;
if|if
condition|(
name|seq
operator|==
literal|null
condition|)
block|{
name|seq
operator|=
operator|new
name|StringBuffer
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|seq
operator|.
name|append
argument_list|(
literal|" and "
argument_list|)
expr_stmt|;
block|}
name|seq
operator|.
name|append
argument_list|(
name|getTextContent
argument_list|(
name|li
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|seq
operator|!=
literal|null
condition|)
block|{
return|return
name|seq
operator|.
name|toString
argument_list|()
return|;
block|}
return|return
literal|null
return|;
block|}
comment|/** 	 * Returns a map of all properties and their values. LIs and bags in seqs 	 * are concatenated using " and ". 	 *  	 * @return Map from name of textproperty (String) to value (String). For 	 *         instance: "year" => "2005". Empty map if none found. 	 * @throws TransformerException 	 */
DECL|method|getAllProperties (XMPSchema schema, String namespaceName)
specifier|public
specifier|static
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|getAllProperties
parameter_list|(
name|XMPSchema
name|schema
parameter_list|,
name|String
name|namespaceName
parameter_list|)
block|{
name|NodeList
name|nodes
init|=
name|schema
operator|.
name|getElement
argument_list|()
operator|.
name|getChildNodes
argument_list|()
decl_stmt|;
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|result
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|()
decl_stmt|;
if|if
condition|(
name|nodes
operator|==
literal|null
condition|)
block|{
return|return
name|result
return|;
block|}
comment|// Check child-nodes first
name|int
name|n
init|=
name|nodes
operator|.
name|getLength
argument_list|()
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
name|n
condition|;
name|i
operator|++
control|)
block|{
name|Node
name|node
init|=
name|nodes
operator|.
name|item
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|node
operator|.
name|getNodeType
argument_list|()
operator|!=
name|Node
operator|.
name|ATTRIBUTE_NODE
operator|&&
name|node
operator|.
name|getNodeType
argument_list|()
operator|!=
name|Node
operator|.
name|ELEMENT_NODE
condition|)
continue|continue;
name|String
name|nodeName
init|=
name|node
operator|.
name|getNodeName
argument_list|()
decl_stmt|;
name|String
index|[]
name|split
init|=
name|nodeName
operator|.
name|split
argument_list|(
literal|":"
argument_list|)
decl_stmt|;
if|if
condition|(
name|split
operator|.
name|length
operator|==
literal|2
operator|&&
name|split
index|[
literal|0
index|]
operator|.
name|equals
argument_list|(
name|namespaceName
argument_list|)
condition|)
block|{
name|NodeList
name|seqList
init|=
operator|(
operator|(
name|Element
operator|)
name|node
operator|)
operator|.
name|getElementsByTagName
argument_list|(
literal|"rdf:Seq"
argument_list|)
decl_stmt|;
if|if
condition|(
name|seqList
operator|.
name|getLength
argument_list|()
operator|>
literal|0
condition|)
block|{
name|String
name|seq
init|=
name|getContents
argument_list|(
name|seqList
argument_list|)
decl_stmt|;
if|if
condition|(
name|seq
operator|!=
literal|null
condition|)
block|{
name|result
operator|.
name|put
argument_list|(
name|split
index|[
literal|1
index|]
argument_list|,
name|seq
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|NodeList
name|bagList
init|=
operator|(
operator|(
name|Element
operator|)
name|node
operator|)
operator|.
name|getElementsByTagName
argument_list|(
literal|"rdf:Bag"
argument_list|)
decl_stmt|;
if|if
condition|(
name|bagList
operator|.
name|getLength
argument_list|()
operator|>
literal|0
condition|)
block|{
name|String
name|seq
init|=
name|getContents
argument_list|(
name|bagList
argument_list|)
decl_stmt|;
if|if
condition|(
name|seq
operator|!=
literal|null
condition|)
block|{
name|result
operator|.
name|put
argument_list|(
name|split
index|[
literal|1
index|]
argument_list|,
name|seq
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|result
operator|.
name|put
argument_list|(
name|split
index|[
literal|1
index|]
argument_list|,
name|getTextContent
argument_list|(
name|node
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
comment|// Then check Attributes
name|NamedNodeMap
name|attrs
init|=
name|schema
operator|.
name|getElement
argument_list|()
operator|.
name|getAttributes
argument_list|()
decl_stmt|;
name|int
name|m
init|=
name|attrs
operator|.
name|getLength
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|j
init|=
literal|0
init|;
name|j
operator|<
name|m
condition|;
name|j
operator|++
control|)
block|{
name|Node
name|attr
init|=
name|attrs
operator|.
name|item
argument_list|(
name|j
argument_list|)
decl_stmt|;
name|String
name|nodeName
init|=
name|attr
operator|.
name|getNodeName
argument_list|()
decl_stmt|;
name|String
index|[]
name|split
init|=
name|nodeName
operator|.
name|split
argument_list|(
literal|":"
argument_list|)
decl_stmt|;
if|if
condition|(
name|split
operator|.
name|length
operator|==
literal|2
operator|&&
name|split
index|[
literal|0
index|]
operator|.
name|equals
argument_list|(
name|namespaceName
argument_list|)
condition|)
block|{
name|result
operator|.
name|put
argument_list|(
name|split
index|[
literal|1
index|]
argument_list|,
name|attr
operator|.
name|getNodeValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
comment|/* 		 * Collapse Whitespace 		 *  		 * Quoting from 		 * http://www.gerg.ca/software/btOOL/doc/bt_postprocess.html:<cite> 		 * "The exact rules for collapsing whitespace are simple: non-space 		 * whitespace characters (tabs and newlines mainly) are converted to 		 * space, any strings of more than one space within are collapsed to a 		 * single space, and any leading or trailing spaces are deleted." 		 *</cite> 		 */
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|entry
range|:
name|result
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|String
name|key
init|=
name|entry
operator|.
name|getKey
argument_list|()
decl_stmt|;
if|if
condition|(
name|preserveWhiteSpace
operator|.
name|contains
argument_list|(
name|key
argument_list|)
condition|)
continue|continue;
name|entry
operator|.
name|setValue
argument_list|(
name|entry
operator|.
name|getValue
argument_list|()
operator|.
name|replaceAll
argument_list|(
literal|"\\s+"
argument_list|,
literal|" "
argument_list|)
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|result
return|;
block|}
DECL|field|preserveWhiteSpace
specifier|public
specifier|static
name|HashSet
argument_list|<
name|String
argument_list|>
name|preserveWhiteSpace
init|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
static|static
block|{
name|preserveWhiteSpace
operator|.
name|add
argument_list|(
literal|"abstract"
argument_list|)
expr_stmt|;
name|preserveWhiteSpace
operator|.
name|add
argument_list|(
literal|"note"
argument_list|)
expr_stmt|;
name|preserveWhiteSpace
operator|.
name|add
argument_list|(
literal|"review"
argument_list|)
expr_stmt|;
block|}
DECL|method|setBibtexEntry (BibtexEntry entry)
specifier|public
name|void
name|setBibtexEntry
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|)
block|{
name|setBibtexEntry
argument_list|(
name|entry
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
comment|/** 	 *  	 * @param entry 	 * @param database maybenull 	 */
DECL|method|setBibtexEntry (BibtexEntry entry, BibtexDatabase database)
specifier|public
name|void
name|setBibtexEntry
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|,
name|BibtexDatabase
name|database
parameter_list|)
block|{
comment|// Set all the values including key and entryType
name|Set
argument_list|<
name|String
argument_list|>
name|fields
init|=
name|entry
operator|.
name|getAllFields
argument_list|()
decl_stmt|;
name|JabRefPreferences
name|prefs
init|=
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
decl_stmt|;
if|if
condition|(
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"useXmpPrivacyFilter"
argument_list|)
condition|)
block|{
name|TreeSet
argument_list|<
name|String
argument_list|>
name|filters
init|=
operator|new
name|TreeSet
argument_list|<
name|String
argument_list|>
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|prefs
operator|.
name|getStringArray
argument_list|(
literal|"xmpPrivacyFilters"
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
name|fields
operator|.
name|removeAll
argument_list|(
name|filters
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|String
name|field
range|:
name|fields
control|)
block|{
name|String
name|value
init|=
name|BibtexDatabase
operator|.
name|getResolvedField
argument_list|(
name|field
argument_list|,
name|entry
argument_list|,
name|database
argument_list|)
decl_stmt|;
if|if
condition|(
name|field
operator|.
name|equals
argument_list|(
literal|"author"
argument_list|)
operator|||
name|field
operator|.
name|equals
argument_list|(
literal|"editor"
argument_list|)
condition|)
block|{
name|setPersonList
argument_list|(
name|field
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|setTextProperty
argument_list|(
name|field
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
block|}
name|setTextProperty
argument_list|(
literal|"entrytype"
argument_list|,
name|entry
operator|.
name|getType
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|getBibtexEntry ()
specifier|public
name|BibtexEntry
name|getBibtexEntry
parameter_list|()
block|{
name|String
name|type
init|=
name|getTextProperty
argument_list|(
literal|"entrytype"
argument_list|)
decl_stmt|;
name|BibtexEntryType
name|t
decl_stmt|;
if|if
condition|(
name|type
operator|!=
literal|null
condition|)
name|t
operator|=
name|BibtexEntryType
operator|.
name|getStandardType
argument_list|(
name|type
argument_list|)
expr_stmt|;
else|else
name|t
operator|=
name|BibtexEntryType
operator|.
name|OTHER
expr_stmt|;
name|BibtexEntry
name|e
init|=
operator|new
name|BibtexEntry
argument_list|(
name|Util
operator|.
name|createNeutralId
argument_list|()
argument_list|,
name|t
argument_list|)
decl_stmt|;
comment|// Get Text Properties
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|text
init|=
name|getAllProperties
argument_list|(
name|this
argument_list|,
literal|"bibtex"
argument_list|)
decl_stmt|;
name|text
operator|.
name|remove
argument_list|(
literal|"entrytype"
argument_list|)
expr_stmt|;
name|e
operator|.
name|setField
argument_list|(
name|text
argument_list|)
expr_stmt|;
return|return
name|e
return|;
block|}
comment|/** 	 * Taken from DOM2Utils.java: 	 *  	 * JBoss, the OpenSource EJB server 	 *  	 * Distributable under LGPL license. See terms of license at gnu.org. 	 */
DECL|method|getTextContent (Node node)
specifier|public
specifier|static
name|String
name|getTextContent
parameter_list|(
name|Node
name|node
parameter_list|)
block|{
name|boolean
name|hasTextContent
init|=
literal|false
decl_stmt|;
name|StringBuffer
name|buffer
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|NodeList
name|nlist
init|=
name|node
operator|.
name|getChildNodes
argument_list|()
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
name|nlist
operator|.
name|getLength
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|Node
name|child
init|=
name|nlist
operator|.
name|item
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|child
operator|.
name|getNodeType
argument_list|()
operator|==
name|Node
operator|.
name|TEXT_NODE
condition|)
block|{
name|buffer
operator|.
name|append
argument_list|(
name|child
operator|.
name|getNodeValue
argument_list|()
argument_list|)
expr_stmt|;
name|hasTextContent
operator|=
literal|true
expr_stmt|;
block|}
block|}
return|return
operator|(
name|hasTextContent
condition|?
name|buffer
operator|.
name|toString
argument_list|()
else|:
literal|""
operator|)
return|;
block|}
block|}
end_class

end_unit

