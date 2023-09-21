<template>



    <div class="start-board" v-if="$store.state.pk.status==='ungameing'">
        <div class="startgame_btn">
            <button type="button" class="btn btn-dark" disabled="disabled">
                难度选择:
            </button>

            <select id="difficulty" style="width: 100px;margin-left: 10px" >
                <option value="1">初级</option>
                <option value="2">中级</option>
                <option value="3">高级</option>
            </select>
            <br>
            <br>
            <button @click="startgame" type="button" class="btn btn-warning">
                开始游戏
            </button>



        </div>
    </div>

    <div class="playground" v-else-if="$store.state.pk.status==='gameing'">
        <TheChessboard @draw="handleDraw" ref="chessboardRef" @move="makeMove" @checkmate="handleCheckmate" @board-created="(api) => (boardAPI = api)" style="width: 400px;padding-top: 10px;" :board-config="boardConfig"  tabindex="0" />
        <h4 id="changecnt">Change Counter:0</h4>
        <h4 id="operator">Operator:White</h4>
        <div class="buttongroup" >
            <button  @click = "prevgame" class="btn btn-primary" style="display:inline-block;margin:10px auto">悔棋</button>
            <button  @click = "resetgame" class="btn btn-primary" style="display:inline-block;margin:10px auto">重新开局</button>
            <button  @click = "falsegame" class="btn btn-primary" style="display:inline-block;margin:10px auto">认输</button>
        </div>

        
    </div>

    <ResultView v-else />

    
    

</template>

<script>


import { onMounted,onUnmounted } from 'vue';

import {useStore} from 'vuex';

import { ref } from 'vue';
import { TheChessboard } from 'vue3-chessboard';
import 'vue3-chessboard/style.css';

import ResultView from './ResultView';

export default{
    components:{
        TheChessboard,
        ResultView,
    },
    setup(){

        const store=useStore();
        const jwt_token=localStorage.getItem("jwt_token");
        const socketUrl=`wss://chess.liaoy0103.top/websocket/${jwt_token}/`;


    
        let socket=null;

        let changecnt=0;


        //console.log(selected_difficulty);

        const boardAPI = ref();

        onMounted(()=>{
            socket = new WebSocket(socketUrl);
            socket.onopen=()=>{
                console.log("connected!");
                store.commit("updateSocket",socket);
                changecnt=0;
                //store.commit("updateResult","");
                //store.commit("updateStatus","ungameing");
            }
            socket.onmessage = msg => { //前端接收到信息时调用的函数

                const data = JSON.parse(msg.data); //不同的框架数据定义的格式不一样
                //console.log("--------");
                console.log(data);
                if(Object.prototype.hasOwnProperty.call(data,'newfen'))
                {
                    store.commit("updateFen",data.newfen);
                    console.log(store.state.pk.fen);
                    boardAPI.value.setPosition(store.state.pk.fen);
                }
                else
                {
                    store.commit("updateFrom",data.from);
                    store.commit("updateTo",data.to);
                    //console.log(store.state.pk.from," ",store.state.pk.to);

                    makeMove(data.from,data.to);
                    store.commit("updateFen",boardAPI.value.getFen());
                }



                
                

                
            }
            socket.onclose = () => { //关闭时调用的函数
                console.log("disconnected!");
                store.commit("updateResult","");
                store.commit("updateStatus","ungameing");
            }
        });

        onUnmounted(() => { //当当前页面关闭时调用
            socket.close(); //卸载的时候断开连接
        });

        


        function handleDraw() {
            alert('Draw');
        }

        function handleCheckmate(isMated) {
            
            if (isMated === 'w') {
                //alert('Black wins!');
            } else {
                //alert('White wins!');
            }
        
        }


        function makeMove(from,to) {
            //console.log(boardConfig.fen+"****"+boardConfig.turnColor);

            //console.log(changecnt);

            if(store.state.pk.status==="gamed")
                return ;

            if(from===null&&to===null)
                return ;
            

            setTimeout(() => {
                changecnt++;
                document.getElementById('changecnt').innerHTML="Change Counter:"+changecnt;

                if(changecnt%2===1)
                {
                //console.log(from,"  ",to,"-----1");
                    store.commit("updateFen",boardAPI.value.getFen());
                    store.state.pk.socket.send(JSON.stringify({
                            fen: store.state.pk.fen,
                            id: store.state.user.id,
                            difficulty: store.state.pk.difficulty,
                            cnt:changecnt,
                            repentance: "0",
                    }));
                    //console.log(store.state.pk.fen);
                    //boardAPI.value.setPosition(store.state.pk.fen);
                    boardAPI.value.board.move(from, to);
                    //boardAPI.value.game.move({from: from, to: to});
                    //console.log(boardAPI.value.game.fen());
                    store.commit("updateFen",boardAPI.value.getFen());
                    //console.log(store.state.pk.fen+"********");
                    //boardAPI.value.setPosition(store.state.pk.fen);
                    document.getElementById('operator').innerHTML="Operator:"+"Black";
                    document.getElementById("operator").style.color="Black";
                }
                else
                {
                    //console.log(to);
                    //console.log(from,"  ",to,"-----2");
                    boardAPI.value.game.move({from: from, to: to });
                    boardAPI.value.board.move(from, to);
                    //console.log(boardAPI.value.game);
                    store.commit("updateFen",boardAPI.value.getFen());
                    store.state.pk.socket.send(JSON.stringify({
                        fen: store.state.pk.fen,
                        id: store.state.user.id,
                        difficulty: store.state.pk.difficulty,
                        cnt:changecnt,
                        repentance: "0",
                    }));
                    boardAPI.value.setPosition(store.state.pk.fen);
                    //console.log("------"+store.state.pk.fen);
                    document.getElementById('operator').innerHTML="Operator:"+"White";
                    document.getElementById("operator").style.color="White";
                }

                //console.log(boardAPI.value.getIsGameOver(),boardAPI.value.getIsStalemate());
                
                if(boardAPI.value.getIsGameOver()===true)
                {
                
                    setTimeout(() => {
                        if(changecnt%2===0)
                        {
                            //console.log("你输了");
                            alert('Black wins!');
                            store.commit("updateResult","lose");
                        }
                        else
                        {
                            //console.log("你赢了");
                            alert('White wins!');
                            store.commit("updateResult","win");
                        }
                    },1000);
                    setTimeout(() => {
                        store.commit("updateStatus","gamed");
                    
                    }, 1500);
                    
                }
        },250);



        }




        const startgame=()=>{

            setTimeout(() => {
                    var difficultys = document.getElementById("difficulty");
                    var index = difficultys.selectedIndex;
                    var selected_difficulty = difficultys.options[index].value;
                    store.commit("updateStatus","gameing");
                    store.commit("updateDifficulty",selected_difficulty);
                    changecnt=0;
                },200);


        }


        const resetgame=()=>{
            boardAPI.value.resetBoard();
            changecnt=0;
            document.getElementById('changecnt').innerHTML="Change Counter:"+changecnt;
            console.log(boardAPI.value.getFen());
        };

        const prevgame=()=>{
            if(changecnt===0) return ;
            store.state.pk.socket.send(JSON.stringify({
                fen: store.state.pk.fen,
                id: store.state.user.id,
                difficulty: store.state.pk.difficulty,
                cnt:changecnt,
                repentance: "1",
            }));
            changecnt--;
            changecnt--;
            document.getElementById('changecnt').innerHTML="Change Counter:"+changecnt;

        };

        const falsegame=()=>{
            alert('Black wins!');
            store.commit("updateResult","lose");
            store.commit("updateStatus","gamed");
        }

        const boardConfig = {

        };

        return{
            changecnt,
            boardConfig,
            boardAPI,
            handleCheckmate,
            handleDraw,
            makeMove,
            startgame,
            resetgame,
            prevgame,
            falsegame,
        }
    }
}

</script>


<style scoped>

div.playground{
    width: 60vw;
    height: 80vh;
    margin: 40px auto;

}

div.gamemap{
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-content: center;
}


#changecnt{
    height: 50px;
    color:red;
    font-weight: 800;
    font-size: 16px;
    text-align: center;
    padding-top: 10px;
    margin:auto;
}

#operator{
    width: 200px;
    height: 50px;
    color: white;
    font-size: 16px;
    font-weight: 800;
    text-align: center;
    padding-top: 17px;
    background-color:lightskyblue;
    margin:auto;
}

#fromto{
    height: 50px;
    color: orange;
    font-weight: 800;
    font-size: 16px;
    text-align: center;
    padding-top: 17px;
    margin:auto;
}
#isselect{
    height: 50px;
    color:white;
    font-weight: 800;
    font-size: 16px;
    text-align: center;
    padding-top: 17px;
    margin:auto;
}

div.start-board{
    height: 30vh;
    width: 30vw;
    background-color: black;
    position: absolute;
    top:30vh;
    left:35vw;
}
div.startgame_btn{
    margin-top: 12vh;
    text-align: center;

}
div.buttongroup{
    display: flex;
    align-items: center;
    justify-content: center;
}


</style>