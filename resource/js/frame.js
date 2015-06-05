$(function(){
    $(".frame_close").bind({
        "mouseover":function(){
            $(this).find("img").attr("src","images/close_hover.png");
        },
        "click":function(){
        	closeFrame();
        },
        "mouseout":function(){
            $(this).find("img").attr("src","images/close.png");
        }
    });

    $(".frame_min").bind({
        "mouseover":function(){
            $(this).find("img").attr("src","images/min_hover.png");
        },
        "click":function(){
        	minFrame();
        },
        "mouseout":function(){
            $(this).find("img").attr("src","images/min.png");
        }
    });
    
    $(".frame_move").bind({
        "mouseover":function(){
            var $this = $(this);
            $this.css("cursor","move");

        },
        "mousedown":function(e){
            var $this = $(this);
            var down_x = e.pageX;
            var down_y = e.pageY;
            window.console.log("startX :"+down_x+" startY:"+down_y);
            $(document).bind({
                "mousemove":fun_move = function(e){

                    var move_x = e.pageX;
                    var move_y = e.pageY;

                    var disX = move_x-down_x;
                    var disY = move_y-down_y;
                    if(Math.abs(disX)>=2|| Math.abs(disY)>=2){
                    	moveFrame(disX,disY);
                    }
                },
                "mouseup":fun_up = function(e){
                	
                	var move_x = e.pageX;
                    var move_y = e.pageY;

                    var disX = move_x-down_x;
                    var disY = move_y-down_y;
                    moveFrame(disX,disY);
                    $(document).unbind({"mousemove":null,"mouseup":null});
                }
            });
        }
    });
});