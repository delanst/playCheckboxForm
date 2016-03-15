package controllers

import models.FormModel
import play.api.data.{Form, Forms}
import play.api.data.Forms._
import play.api.mvc.{Action, Controller}

object Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }


  val checkboxForm = Form(
      Forms.mapping(
        "firstName" -> text(maxLength = 30) ,
        "lastName" -> text(maxLength = 30),
        "getData" -> optional(checked("Get data"))
      )(FormModel.apply)(FormModel.unapply)
  )

  def inputPage = Action { implicit request =>
    Ok(views.html.input(checkboxForm,FormModel("","",Some(false))))
  }

  def outputPage = Action { implicit request =>
    Ok(views.html.output())
  }

  def submitCheckBoxForm = Action { implicit  request =>
    checkboxForm.bindFromRequest.fold(
      formWithErrors => {
        println("Form error =>")
        formWithErrors.errors.foreach(value => println(value.message))
        Ok(views.html.input(checkboxForm,FormModel("","",Some(false))))
      } ,
      data => {
        println("FirstName = " + data.firstName + ",lastName=" + data.lastName + ",getData=" + data.getData.getOrElse(false))
        Ok(views.html.input(checkboxForm,FormModel(data.firstName,data.lastName,data.getData)))
      }
    )
  }


}