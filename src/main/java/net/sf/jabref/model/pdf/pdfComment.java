begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.model.pdf
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|pdf
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Optional
import|;
end_import

begin_class
DECL|class|pdfComment
specifier|public
class|class
name|pdfComment
block|{
DECL|field|commentId
specifier|private
name|String
name|commentId
decl_stmt|;
DECL|field|author
specifier|private
name|String
name|author
decl_stmt|;
DECL|field|date
specifier|private
name|String
name|date
decl_stmt|;
DECL|field|page
specifier|private
name|int
name|page
decl_stmt|;
DECL|field|content
specifier|private
name|String
name|content
decl_stmt|;
DECL|field|text
specifier|private
name|Optional
argument_list|<
name|String
argument_list|>
name|text
decl_stmt|;
DECL|method|pdfComment (String commentId, String author, String date, int page, String content, Optional<String> text)
specifier|public
name|pdfComment
parameter_list|(
name|String
name|commentId
parameter_list|,
name|String
name|author
parameter_list|,
name|String
name|date
parameter_list|,
name|int
name|page
parameter_list|,
name|String
name|content
parameter_list|,
name|Optional
argument_list|<
name|String
argument_list|>
name|text
parameter_list|)
block|{
name|this
operator|.
name|author
operator|=
name|author
expr_stmt|;
name|this
operator|.
name|date
operator|=
name|date
expr_stmt|;
name|this
operator|.
name|page
operator|=
name|page
expr_stmt|;
name|this
operator|.
name|content
operator|=
name|content
expr_stmt|;
name|this
operator|.
name|text
operator|=
name|text
expr_stmt|;
block|}
DECL|method|getCommentId ()
specifier|public
name|String
name|getCommentId
parameter_list|()
block|{
return|return
name|commentId
return|;
block|}
DECL|method|setCommentId (String commentId)
specifier|public
name|void
name|setCommentId
parameter_list|(
name|String
name|commentId
parameter_list|)
block|{
name|this
operator|.
name|commentId
operator|=
name|commentId
expr_stmt|;
block|}
DECL|method|getAuthor ()
specifier|public
name|String
name|getAuthor
parameter_list|()
block|{
return|return
name|author
return|;
block|}
DECL|method|setAuthor (String author)
specifier|public
name|void
name|setAuthor
parameter_list|(
name|String
name|author
parameter_list|)
block|{
name|this
operator|.
name|author
operator|=
name|author
expr_stmt|;
block|}
DECL|method|getDate ()
specifier|public
name|String
name|getDate
parameter_list|()
block|{
return|return
name|date
return|;
block|}
DECL|method|setDate (String date)
specifier|public
name|void
name|setDate
parameter_list|(
name|String
name|date
parameter_list|)
block|{
name|this
operator|.
name|date
operator|=
name|date
expr_stmt|;
block|}
DECL|method|getPage ()
specifier|public
name|int
name|getPage
parameter_list|()
block|{
return|return
name|page
return|;
block|}
DECL|method|setPage (int page)
specifier|public
name|void
name|setPage
parameter_list|(
name|int
name|page
parameter_list|)
block|{
name|this
operator|.
name|page
operator|=
name|page
expr_stmt|;
block|}
DECL|method|getContent ()
specifier|public
name|String
name|getContent
parameter_list|()
block|{
return|return
name|content
return|;
block|}
DECL|method|setContent (String content)
specifier|public
name|void
name|setContent
parameter_list|(
name|String
name|content
parameter_list|)
block|{
name|this
operator|.
name|content
operator|=
name|content
expr_stmt|;
block|}
DECL|method|getText ()
specifier|public
name|Optional
argument_list|<
name|String
argument_list|>
name|getText
parameter_list|()
block|{
return|return
name|text
return|;
block|}
DECL|method|setText (Optional<String> text)
specifier|public
name|void
name|setText
parameter_list|(
name|Optional
argument_list|<
name|String
argument_list|>
name|text
parameter_list|)
block|{
name|this
operator|.
name|text
operator|=
name|text
expr_stmt|;
block|}
block|}
end_class

end_unit

