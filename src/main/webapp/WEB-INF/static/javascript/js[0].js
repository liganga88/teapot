// JavaScript Document

$(function(){
	var main = $('.main')
	var winHeight = $(window).innerHeight()
	var mainHeight = winHeight-380
	
	main.css('min-height',mainHeight)
	
	$(window).resize(function(){
		var main = $('.main')
		var winHeight = $(window).innerHeight()
		var mainHeight = winHeight-380
		
		main.height('min-height',mainHeight)
	})
})