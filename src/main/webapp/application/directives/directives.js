var directives = angular.module('directives', []);

/** Configuração do módulo de diretivas da aplicação. */
directives.config([ '$httpProvider', function($httpProvider) {

	/**
	 * Implementação de 'interceptor' para obter a quantidades total de
	 * requisições ocorridas nas iterações ocorridas na aplicação.
	 * 
	 * 
	 * Importante - O loader da aplicação não está usando a implementação de
	 * modal do bootstrap para evitar o bug de escroll, mas o estilo está sendo
	 * adotado e invocado com o Jquery.
	 */
	$httpProvider.interceptors.push(function($q, $rootScope) {
		var count = 0;

		return {
			request : function(config) {
				if (count <= 0) {
					$('.loader').show();
				}
				++count;
				return config;
			},
			requestError : function(request) {
				if (!(--count)) {
					$('.loader').hide();
				}
				return $q.reject(request);
			},
			response : function(response) {
				if ((--count) === 0) {
					$('.loader').hide();
				}
				return response;
			},
			responseError : function(response) {
				if (!(--count)) {
					$('.loader').hide();
				}
				return $q.reject(response);
			}
		};
	});

} ]);

/**
 * Realiza o carregamento dos templates das directivas.
 */
directives
		.run([
				'$templateCache',
				function($templateCache) {

					/** Template referente Modal Loader. */
					$templateCache
							.put(
									'modalLoader.html',
									'<div class="loader modal" >'
											+ '<div class="modal-backdrop  in" style="min-height: 100%;"></div>'
											+ '<div class="modal-dialog"> <div class="modal-content">'
											+ '<div class="modal-header" style="text-align: center"><h5 class="modal-title">Aguarde</h5></div>'
											+ '<div class="modal-body" ><div class="row row-mg-1 texto-centro" ><img src="resources/img/ajax-loader.gif"></div>'
											+ '</div></div></div></div>');

					$('body').append($templateCache.get('modalLoader.html'));
				} ]);

/**
 * Implementação de 'directive', para a geração do componente ngPopup.
 */
directives
		.directive(
				'ngPopup',
				[ function() {
					return {
						restrinct : 'A',
						template : function(element, attrs) {
							var html = '<div class="modal fade" tabindex="-1" role="modal" ><div class="modal-dialog" ';
							if (attrs.width) {
								var width = attrs.width.trim().substring(
										attrs.width.trim().length - 2,
										attrs.width.trim().length) === 'px' ? attrs.width
										: attrs.width + 'px';
								$(window)
										.resize(
												function() {
													if ($(window).width() < width
															.substring(
																	0,
																	'800px'.length - 2)) {
														$(element)
																.find(
																		'.modal-dialog')
																.css("width",
																		'100%');
													} else {
														$(element)
																.find(
																		'.modal-dialog')
																.css("width",
																		width);
													}
												});

								html = html + 'style="width:' + width + '"';
							}
							return html
									+ '><div class="modal-content" ng-transclude ></div></div></div>';
						},
						replace : true,
						transclude : true
					}
				} ]);

/**
 * Implementação de 'factory', para o controle do componente ngPopup.
 */
directives
		.factory('$popup',
				[
						'$rootScope',
						'$timeout',
						function($rootScope, $timeout) {

							/**
							 * Retorna o id formatado removendo os espaços e
							 * adicionando '#' caso não seja informado.
							 * 
							 * @param $id
							 */
							var getIdFormatado = function($id) {
								return angular.equals($id.trim()
										.substring(1, 0), '#') ? $id.trim()
										: '#' + $id.trim();
							};

							/**
							 * Invoca o evento referente a ação informada.
							 * 
							 * @param $id
							 * @param event
							 */
							var startEvent = function($id, action) {
								var event = angular.equals($id.trim()
										.substring(1, 0), '#') ? $id.trim()
										.substring(1, $id.trim().length) : $id
										.trim();
								event += '.' + action;

								$rootScope.$broadcast(event);
							};

							/**
							 * Abre o Popup por Id.
							 * 
							 * @param $id
							 */
							this.show = function($id) {
								if ($id) {
									$timeout(function() {
										startEvent($id, 'show');

										var id = getIdFormatado($id);
										$(id).modal({
											backdrop : 'static',
											keyboard : true
										});
									});
								}
							};

							/**
							 * Fecha o Popup por Id.
							 * 
							 * @param $id
							 */
							this.hide = function($id) {
								if ($id) {
									$timeout(function() {
										startEvent($id, 'hide');

										var id = getIdFormatado($id);
										$(id).modal('hide');
									});
								}
							};

							/**
							 * Realiza ação alternada, caso o modal esteja
							 * fechado será aberto, caso o modal esteja aberto
							 * será fechado.
							 * 
							 * @param $id
							 */
							this.toggle = function($id) {
								if ($id) {
									$timeout(function() {
										startEvent($id, 'toggle');

										var id = getIdFormatado($id);
										$(id).modal('toggle');
									})
								}
							};

							return this;
						} ]);

/**
 * Implementação de 'directive', para a geração do componente ngShowPopup.
 */
directives.directive('ngShowPopup', [ '$popup', function($popup) {
	return {
		restrinct : 'A',
		link : function(scope, element, attrs) {
			if (attrs.ngShowPopup) {

				$(element).on('click', function() {
					$popup.show(attrs.ngShowPopup);
				});
			}
		}
	}
} ]);

/**
 * Implementação de 'directive', para a geração do componente ngHidePopup.
 */
directives.directive('ngHidePopup', [ '$popup', function($popup) {
	return {
		restrinct : 'A',
		link : function(scope, element, attrs) {
			if (attrs.ngHidePopup) {

				$(element).on('click', function() {
					$popup.hide(attrs.ngHidePopup);
				});
			}
			;
		}
	}
} ]);

/**
 * Seta o tabindex passado por parametro no elemento e adiciona o manipulador de
 * evento keydow para controlar a tabulação com enter entre os elementos do
 * formulario.
 */
directives
		.directive(
				'ngTabindex',
				function() {
					return {
						restrinct : 'A',
						link : function(scope, element, attrs) {
							$(element).attr('tabindex', attrs.ngTabindex);
							$(element)
									.keydown(
											function(event) {
												var keyCode = event.keyCode ? event.keyCode
														: event.which ? event.which
																: event.charCode;
												if (keyCode == 13) {
													event.preventDefault();
													var field = document
															.getElementById($(
																	this).attr(
																	'id'));
													var i;
													for (i = 0; i < field.form.elements.length; i++)
														if (field.form.elements[i].tabIndex == field.tabIndex + 1) {
															field.form.elements[i]
																	.focus();
														}
												}

											});
						}
					}
				});

/**
 * Implementação de 'directive', para criar mascaras.
 * 
 * Use "data-ng-maskedinput", e passe a mascara desejada como parametro.
 */
directives.directive('ngMaskedinput', function() {
	return {
		restrinct : 'A',
		link : function(scope, element, attrs) {
			$(element).mask(attrs.ngMaskedinput);
		}
	}
});

/**
 * Implementação de 'directive', para controlar o Foco.
 * 
 * O componente que estiver com "ng-focus", recebe o Foco.
 */
directives.directive('focus', function($timeout) {
	return {

		restrinct : 'A',

		link : function(scope, element) {
			$timeout(function() {
				element[0].focus();
			});
		}
	};
});

/**
 * Implementação de 'directive', para controlar a ordenacao por coluna nas
 * tabelas Ex Uso:
 * <th data-order data-ng-click="order('fieldName')">FieldName <div
 * data-ng-show="fieldName == 'fieldName'" data-ng-class="{reverse:reverse}">
 * Obs: o nome da variavel no escopo do controller que controla a lista deve
 * obrigatoriamente chamar 'listaTabela'
 */
directives
		.directive(
				'order',
				[
						'$filter',
						function($filter) {
							return {
								restrinct : 'A',

								link : function(scope, element) {
									var orderBy = $filter('orderBy');
									$(element).children().addClass('sortable');

									scope.order = function(fieldName) {
										scope.fieldName = fieldName;
										scope.reverse = (scope.fieldName === fieldName) ? !scope.reverse
												: false;
										scope.listaTabela = orderBy(
												scope.listaTabela, fieldName,
												scope.reverse);
									};

									scope.isReverse = function(fieldName) {
										return (scope.reverse && scope.fieldName == fieldName);
									}
								}
							}

						} ]);

/**
 * Implementação de 'directive', para Abrir o Calendário.
 * 
 * O input que estiver com "datepicker", abre o calendário ao ser clicado.
 */
directives.directive('datepicker', function() {
	return {
		restrict : 'A',
		require : 'ngModel',
		link : function(scope, element, attrs, ngModelCtrl) {

			$(function() {

				element.datepicker({
					format : 'dd/mm/yyyy',
					autoclose : true,
					startDate : '01/01/1900',
					endDate : '31/12/2999',
					todayHighlight : true ,
					clearBtn : true
				});
			});

			$(element).on('focus', function() {
				if (attrs.inicial != undefined) {
					$(element).datepicker('setStartDate', attrs.inicial);
				}
			});
		}
	}
});

/**
 * Implementação de 'directive', para Formatar Data.
 * 
 */
directives.directive('dataformat', function (dateFilter) {
    return {
        require:'ngModel',
        link:function (scope, elm, attrs, ctrl) {

            var dateFormat = attrs['date'] || 'dd/MM/yyyy HH:mm:ss';
           
            ctrl.$formatters.unshift(function (modelValue) {
                return dateFilter(modelValue, dateFormat);
            });
        }
    };
})


/**
 * Implementação de 'directive', para AutoDimensionar Linhas da Tabela.
 * 
 */
directives.directive('heigth', function($timeout) {
	
		return {
			restrinct : 'A',
			link : function(scope, element) {
				var funcao = function(){
					var height = element.css('height');
		            element.css('height',height);
			    }
			 
				$timeout(function() {
					funcao();
				})
			}
		}

});

/**
 * Diretiva para mascara monetaria em input text.
 */
directives.directive('maskMoney', function() {
	return {
		restrinct : 'A',
		link : function(scope, element) {
			element.maskMoney({
				showSymbol:true,
				symbol:"R$",
				decimal:",",
				thousands: "."
			});
		}
	}
});


/**
 * Filtro Responsavel por Formatar CNPJ
 */
directives.filter('cnpj', function() {
	  return function(input) {
	  	var str = input+ '';
	        str=str.replace(/\D/g,'');
	    	str=str.replace(/^(\d{2})(\d)/,'$1.$2');
	    	str=str.replace(/^(\d{2})\.(\d{3})(\d)/,'$1.$2.$3');
	    	str=str.replace(/\.(\d{3})(\d)/,'.$1/$2');
	    	str=str.replace(/(\d{4})(\d)/,'$1-$2');
	    return str;
	  };
	});