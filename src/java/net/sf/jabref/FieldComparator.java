begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
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
name|BibtexEntry
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
name|GUIGlobals
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
name|AuthorList
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
name|imports
operator|.
name|ImportFormatReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Comparator
import|;
end_import

begin_comment
comment|/**  * Created by IntelliJ IDEA.  * User: alver  * Date: Oct 13, 2005  * Time: 10:10:04 PM  * To change this template use File | Settings | File Templates.  */
end_comment

begin_class
DECL|class|FieldComparator
specifier|public
class|class
name|FieldComparator
implements|implements
name|Comparator
block|{
DECL|field|field
specifier|private
name|String
name|field
decl_stmt|;
DECL|field|isNameField
DECL|field|isTypeHeader
specifier|private
name|boolean
name|isNameField
decl_stmt|,
name|isTypeHeader
decl_stmt|;
DECL|field|multiplier
specifier|private
name|int
name|multiplier
decl_stmt|;
DECL|method|FieldComparator (String field)
specifier|public
name|FieldComparator
parameter_list|(
name|String
name|field
parameter_list|)
block|{
name|this
argument_list|(
name|field
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
DECL|method|FieldComparator (String field, boolean reversed)
specifier|public
name|FieldComparator
parameter_list|(
name|String
name|field
parameter_list|,
name|boolean
name|reversed
parameter_list|)
block|{
name|this
operator|.
name|field
operator|=
name|field
expr_stmt|;
name|multiplier
operator|=
name|reversed
condition|?
operator|-
literal|1
else|:
literal|1
expr_stmt|;
name|isNameField
operator|=
operator|(
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
operator|)
expr_stmt|;
name|isTypeHeader
operator|=
name|field
operator|.
name|equals
argument_list|(
name|GUIGlobals
operator|.
name|TYPE_HEADER
argument_list|)
expr_stmt|;
block|}
DECL|method|compare (Object o1, Object o2)
specifier|public
name|int
name|compare
parameter_list|(
name|Object
name|o1
parameter_list|,
name|Object
name|o2
parameter_list|)
block|{
name|BibtexEntry
name|e1
init|=
operator|(
name|BibtexEntry
operator|)
name|o1
decl_stmt|,
name|e2
init|=
operator|(
name|BibtexEntry
operator|)
name|o2
decl_stmt|;
name|Object
name|f1
init|=
name|e1
operator|.
name|getField
argument_list|(
name|field
argument_list|)
decl_stmt|,
name|f2
init|=
name|e2
operator|.
name|getField
argument_list|(
name|field
argument_list|)
decl_stmt|;
comment|// If the field is author or editor, we rearrange names so they are
comment|// sorted according to last name.
if|if
condition|(
name|isNameField
condition|)
block|{
if|if
condition|(
name|f1
operator|!=
literal|null
condition|)
name|f1
operator|=
name|AuthorList
operator|.
name|fixAuthorForAlphabetization
argument_list|(
operator|(
name|String
operator|)
name|f1
argument_list|)
expr_stmt|;
if|if
condition|(
name|f2
operator|!=
literal|null
condition|)
name|f2
operator|=
name|AuthorList
operator|.
name|fixAuthorForAlphabetization
argument_list|(
operator|(
name|String
operator|)
name|f2
argument_list|)
expr_stmt|;
comment|//System.out.println(".. "+f1);
block|}
elseif|else
if|if
condition|(
name|isTypeHeader
condition|)
block|{
comment|// Sort by type.
name|f1
operator|=
name|e1
operator|.
name|getType
argument_list|()
operator|.
name|getName
argument_list|()
expr_stmt|;
name|f2
operator|=
name|e2
operator|.
name|getType
argument_list|()
operator|.
name|getName
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
operator|(
name|f1
operator|==
literal|null
operator|)
operator|&&
operator|(
name|f2
operator|==
literal|null
operator|)
condition|)
return|return
literal|0
return|;
if|if
condition|(
operator|(
name|f1
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|f2
operator|==
literal|null
operator|)
condition|)
return|return
operator|-
literal|1
operator|*
name|multiplier
return|;
if|if
condition|(
name|f1
operator|==
literal|null
condition|)
return|return
name|multiplier
return|;
name|int
name|result
init|=
literal|0
decl_stmt|;
comment|//System.out.println(f1);
if|if
condition|(
operator|(
name|f1
operator|instanceof
name|Integer
operator|)
operator|&&
operator|(
name|f2
operator|instanceof
name|Integer
operator|)
condition|)
block|{
name|result
operator|=
operator|-
operator|(
operator|(
operator|(
name|Integer
operator|)
name|f1
operator|)
operator|.
name|compareTo
argument_list|(
operator|(
name|Integer
operator|)
name|f2
argument_list|)
operator|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|f2
operator|instanceof
name|Integer
condition|)
block|{
name|Integer
name|f1AsInteger
init|=
operator|new
name|Integer
argument_list|(
name|f1
operator|.
name|toString
argument_list|()
argument_list|)
decl_stmt|;
name|result
operator|=
operator|-
operator|(
operator|(
name|f1AsInteger
operator|)
operator|.
name|compareTo
argument_list|(
operator|(
name|Integer
operator|)
name|f2
argument_list|)
operator|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|f1
operator|instanceof
name|Integer
condition|)
block|{
name|Integer
name|f2AsInteger
init|=
operator|new
name|Integer
argument_list|(
name|f2
operator|.
name|toString
argument_list|()
argument_list|)
decl_stmt|;
name|result
operator|=
operator|-
operator|(
operator|(
operator|(
name|Integer
operator|)
name|f1
operator|)
operator|.
name|compareTo
argument_list|(
name|f2AsInteger
argument_list|)
operator|)
expr_stmt|;
block|}
else|else
block|{
name|String
name|ours
init|=
operator|(
operator|(
name|String
operator|)
name|f1
operator|)
operator|.
name|toLowerCase
argument_list|()
decl_stmt|,
name|theirs
init|=
operator|(
operator|(
name|String
operator|)
name|f2
operator|)
operator|.
name|toLowerCase
argument_list|()
decl_stmt|;
comment|//System.out.println(ours);
name|result
operator|=
name|ours
operator|.
name|compareTo
argument_list|(
name|theirs
argument_list|)
expr_stmt|;
block|}
return|return
name|result
operator|*
name|multiplier
return|;
block|}
block|}
end_class

end_unit

