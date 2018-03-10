import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ChangeAppServerTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { DefinitionConfigDetailComponent } from '../../../../../../main/webapp/app/entities/definition-config/definition-config-detail.component';
import { DefinitionConfigService } from '../../../../../../main/webapp/app/entities/definition-config/definition-config.service';
import { DefinitionConfig } from '../../../../../../main/webapp/app/entities/definition-config/definition-config.model';

describe('Component Tests', () => {

    describe('DefinitionConfig Management Detail Component', () => {
        let comp: DefinitionConfigDetailComponent;
        let fixture: ComponentFixture<DefinitionConfigDetailComponent>;
        let service: DefinitionConfigService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ChangeAppServerTestModule],
                declarations: [DefinitionConfigDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    DefinitionConfigService,
                    JhiEventManager
                ]
            }).overrideTemplate(DefinitionConfigDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DefinitionConfigDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DefinitionConfigService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new DefinitionConfig(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.definitionConfig).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
