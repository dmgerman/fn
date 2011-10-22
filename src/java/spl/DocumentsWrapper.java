begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|spl
package|package
name|spl
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
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Vector
import|;
end_import

begin_import
import|import
name|org
operator|.
name|sciplore
operator|.
name|beans
operator|.
name|Author
import|;
end_import

begin_import
import|import
name|org
operator|.
name|sciplore
operator|.
name|beans
operator|.
name|Document
import|;
end_import

begin_import
import|import
name|org
operator|.
name|sciplore
operator|.
name|beans
operator|.
name|Year
import|;
end_import

begin_import
import|import
name|org
operator|.
name|sciplore
operator|.
name|formatter
operator|.
name|Bean
import|;
end_import

begin_import
import|import
name|org
operator|.
name|sciplore
operator|.
name|formatter
operator|.
name|SimpleTypeElementBean
import|;
end_import

begin_comment
comment|/**  * Created by IntelliJ IDEA.  * User: Christoph Arbeit  * Date: 09.09.2010  * Time: 10:56:50  * To change this template use File | Settings | File Templates.  */
end_comment

begin_class
DECL|class|DocumentsWrapper
specifier|public
class|class
name|DocumentsWrapper
block|{
DECL|field|xmlDocuments
name|Document
name|xmlDocuments
decl_stmt|;
DECL|method|DocumentsWrapper (Document documents)
specifier|public
name|DocumentsWrapper
parameter_list|(
name|Document
name|documents
parameter_list|)
block|{
name|this
operator|.
name|xmlDocuments
operator|=
name|documents
expr_stmt|;
block|}
DECL|method|getXmlDocuments ()
specifier|public
name|Document
name|getXmlDocuments
parameter_list|()
block|{
return|return
name|xmlDocuments
return|;
block|}
DECL|method|setXmlDocuments (Document xmlDocuments)
specifier|public
name|void
name|setXmlDocuments
parameter_list|(
name|Document
name|xmlDocuments
parameter_list|)
block|{
name|this
operator|.
name|xmlDocuments
operator|=
name|xmlDocuments
expr_stmt|;
block|}
DECL|method|getDocuments ()
specifier|public
name|List
argument_list|<
name|Vector
argument_list|>
name|getDocuments
parameter_list|()
block|{
name|List
argument_list|<
name|Vector
argument_list|>
name|documents
init|=
operator|new
name|ArrayList
argument_list|<
name|Vector
argument_list|>
argument_list|()
decl_stmt|;
comment|//List<XmlDocument> xmlDocuments = this.xmlDocuments.getDocuments();
comment|//for(XmlDocument xmlDocument : xmlDocuments){
name|Document
name|xmlDocument
init|=
name|xmlDocuments
decl_stmt|;
name|Vector
argument_list|<
name|String
argument_list|>
name|vector
init|=
operator|new
name|Vector
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
if|if
condition|(
name|xmlDocument
operator|.
name|getTitle
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|vector
operator|.
name|add
argument_list|(
name|xmlDocument
operator|.
name|getTitle
argument_list|()
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|vector
operator|.
name|add
argument_list|(
literal|""
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|xmlDocument
operator|.
name|getAuthors
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|List
argument_list|<
name|Bean
argument_list|>
name|authors
init|=
name|xmlDocument
operator|.
name|getAuthors
argument_list|()
operator|.
name|getCollection
argument_list|()
decl_stmt|;
name|authors
operator|=
name|this
operator|.
name|sortAuthors
argument_list|(
name|authors
argument_list|)
expr_stmt|;
name|String
name|value
init|=
literal|""
decl_stmt|;
name|int
name|i
init|=
literal|1
decl_stmt|;
for|for
control|(
name|Bean
name|author
range|:
name|authors
control|)
block|{
if|if
condition|(
name|i
operator|<
name|authors
operator|.
name|size
argument_list|()
condition|)
block|{
name|value
operator|=
name|value
operator|+
name|getNameComplete
argument_list|(
operator|(
operator|(
name|Author
operator|)
name|author
operator|)
argument_list|)
expr_stmt|;
name|value
operator|=
name|value
operator|+
literal|", "
expr_stmt|;
block|}
else|else
block|{
name|value
operator|=
name|value
operator|+
name|getNameComplete
argument_list|(
operator|(
operator|(
name|Author
operator|)
name|author
operator|)
argument_list|)
expr_stmt|;
block|}
name|i
operator|++
expr_stmt|;
block|}
name|vector
operator|.
name|add
argument_list|(
name|value
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|vector
operator|.
name|add
argument_list|(
literal|""
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|xmlDocument
operator|.
name|getYear
argument_list|()
operator|!=
literal|null
operator|&&
operator|(
operator|(
name|Year
operator|)
name|xmlDocument
operator|.
name|getYear
argument_list|()
operator|)
operator|.
name|getValue
argument_list|()
operator|!=
literal|null
operator|&&
operator|!
operator|(
operator|(
name|Year
operator|)
name|xmlDocument
operator|.
name|getYear
argument_list|()
operator|)
operator|.
name|getValue
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"null"
argument_list|)
condition|)
block|{
name|vector
operator|.
name|add
argument_list|(
operator|(
operator|(
name|Year
operator|)
name|xmlDocument
operator|.
name|getYear
argument_list|()
operator|)
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/*if(xmlDocument.getPublishdate() != null&& xmlDocument.getPublishdate().getYear() != null&& !xmlDocument.getPublishdate().getYear().equalsIgnoreCase("null")){                 vector.add(xmlDocument.getPublishdate().getYear());             }*/
else|else
block|{
name|vector
operator|.
name|add
argument_list|(
literal|""
argument_list|)
expr_stmt|;
block|}
name|documents
operator|.
name|add
argument_list|(
name|vector
argument_list|)
expr_stmt|;
comment|//}
return|return
name|documents
return|;
block|}
DECL|method|getNameComplete (Author author)
specifier|private
name|String
name|getNameComplete
parameter_list|(
name|Author
name|author
parameter_list|)
block|{
if|if
condition|(
name|author
operator|==
literal|null
condition|)
return|return
literal|""
return|;
name|String
name|result
init|=
literal|""
decl_stmt|;
if|if
condition|(
name|getSimpleTypeValue
argument_list|(
name|author
operator|.
name|getName_First
argument_list|()
argument_list|)
operator|!=
literal|null
condition|)
name|result
operator|=
name|result
operator|+
name|getSimpleTypeValue
argument_list|(
name|author
operator|.
name|getName_First
argument_list|()
argument_list|)
operator|.
name|trim
argument_list|()
operator|+
literal|" "
expr_stmt|;
if|if
condition|(
name|getSimpleTypeValue
argument_list|(
name|author
operator|.
name|getName_Middle
argument_list|()
argument_list|)
operator|!=
literal|null
condition|)
name|result
operator|=
name|result
operator|+
name|getSimpleTypeValue
argument_list|(
name|author
operator|.
name|getName_Middle
argument_list|()
argument_list|)
operator|.
name|trim
argument_list|()
operator|+
literal|" "
expr_stmt|;
if|if
condition|(
name|getSimpleTypeValue
argument_list|(
name|author
operator|.
name|getName_Last_Prefix
argument_list|()
argument_list|)
operator|!=
literal|null
condition|)
name|result
operator|=
name|result
operator|+
name|getSimpleTypeValue
argument_list|(
name|author
operator|.
name|getName_Last_Prefix
argument_list|()
argument_list|)
operator|.
name|trim
argument_list|()
operator|+
literal|" "
expr_stmt|;
if|if
condition|(
name|getSimpleTypeValue
argument_list|(
name|author
operator|.
name|getName_Last
argument_list|()
argument_list|)
operator|!=
literal|null
condition|)
name|result
operator|=
name|result
operator|+
name|getSimpleTypeValue
argument_list|(
name|author
operator|.
name|getName_Last
argument_list|()
argument_list|)
operator|.
name|trim
argument_list|()
operator|+
literal|" "
expr_stmt|;
if|if
condition|(
name|getSimpleTypeValue
argument_list|(
name|author
operator|.
name|getName_Last_Suffix
argument_list|()
argument_list|)
operator|!=
literal|null
condition|)
name|result
operator|=
name|result
operator|+
name|getSimpleTypeValue
argument_list|(
name|author
operator|.
name|getName_Last_Suffix
argument_list|()
argument_list|)
operator|.
name|trim
argument_list|()
operator|+
literal|" "
expr_stmt|;
return|return
name|result
operator|.
name|trim
argument_list|()
return|;
block|}
DECL|method|getSimpleTypeValue (Bean bean)
specifier|private
name|String
name|getSimpleTypeValue
parameter_list|(
name|Bean
name|bean
parameter_list|)
block|{
if|if
condition|(
name|bean
operator|==
literal|null
operator|||
operator|!
operator|(
name|bean
operator|instanceof
name|SimpleTypeElementBean
operator|)
condition|)
return|return
literal|null
return|;
name|SimpleTypeElementBean
name|simpleTypeElementBean
init|=
operator|(
name|SimpleTypeElementBean
operator|)
name|bean
decl_stmt|;
if|if
condition|(
name|simpleTypeElementBean
operator|.
name|getValue
argument_list|()
operator|==
literal|null
operator|||
name|simpleTypeElementBean
operator|.
name|getValue
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"null"
argument_list|)
operator|||
name|simpleTypeElementBean
operator|.
name|getValue
argument_list|()
operator|.
name|length
argument_list|()
operator|<=
literal|0
condition|)
return|return
literal|null
return|;
return|return
name|simpleTypeElementBean
operator|.
name|getValue
argument_list|()
return|;
block|}
DECL|method|sortAuthors (List<Bean> authors)
specifier|private
name|List
argument_list|<
name|Bean
argument_list|>
name|sortAuthors
parameter_list|(
name|List
argument_list|<
name|Bean
argument_list|>
name|authors
parameter_list|)
block|{
name|boolean
name|unsorted
init|=
literal|true
decl_stmt|;
name|Bean
name|temp
decl_stmt|;
while|while
condition|(
name|unsorted
condition|)
block|{
name|unsorted
operator|=
literal|false
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|authors
operator|.
name|size
argument_list|()
operator|-
literal|1
condition|;
name|i
operator|++
control|)
block|{
name|int
name|rank
init|=
literal|99
decl_stmt|;
name|int
name|otherRank
init|=
literal|99
decl_stmt|;
if|if
condition|(
operator|(
operator|(
name|Author
operator|)
name|authors
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|)
operator|.
name|getRank
argument_list|()
operator|!=
literal|null
operator|&&
operator|!
operator|(
operator|(
name|Author
operator|)
name|authors
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|)
operator|.
name|getRank
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"null"
argument_list|)
condition|)
block|{
name|rank
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
operator|(
operator|(
name|Author
operator|)
name|authors
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|)
operator|.
name|getRank
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|(
operator|(
name|Author
operator|)
name|authors
operator|.
name|get
argument_list|(
name|i
operator|+
literal|1
argument_list|)
operator|)
operator|.
name|getRank
argument_list|()
operator|!=
literal|null
operator|&&
operator|!
operator|(
operator|(
name|Author
operator|)
name|authors
operator|.
name|get
argument_list|(
name|i
operator|+
literal|1
argument_list|)
operator|)
operator|.
name|getRank
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"null"
argument_list|)
condition|)
block|{
name|otherRank
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
operator|(
operator|(
name|Author
operator|)
name|authors
operator|.
name|get
argument_list|(
name|i
operator|+
literal|1
argument_list|)
operator|)
operator|.
name|getRank
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|rank
operator|>
name|otherRank
condition|)
block|{
name|temp
operator|=
name|authors
operator|.
name|get
argument_list|(
name|i
argument_list|)
expr_stmt|;
name|authors
operator|.
name|set
argument_list|(
name|i
argument_list|,
name|authors
operator|.
name|get
argument_list|(
name|i
operator|+
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|authors
operator|.
name|set
argument_list|(
name|i
operator|+
literal|1
argument_list|,
name|temp
argument_list|)
expr_stmt|;
name|unsorted
operator|=
literal|true
expr_stmt|;
block|}
block|}
block|}
return|return
name|authors
return|;
block|}
block|}
end_class

end_unit

